package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.compiler.context.Environment;
import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;
import org.unq.compiler.remolacha.grammar.Program;
import org.unq.compiler.remolacha.grammar.expressions.Send;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mtejeda on 14/11/17.
 * Clase que va a generar la mayor cantidad de código del compilador
 */
public class CodeHelper {

    private static final String header =
            "#include <cstdlib>\n" +
            "#include <cstdio>\n" +
            "#include <iostream>\n" +
            "#include <stdlib.h>\n" +
            "typedef unsigned long long int Num;\n" +
            "typedef const char* String;\n" +
            "typedef void* PTR;\n" +
            "using namespace std;\n" +
            "struct Clase {\n" +
            "   PTR* metodos;\n" +
            "};\n" +
            "struct Objeto {\n" +
            "   Clase* clase;\n" +
            "   PTR* varsInstancia;\n" +
            "};\n" +
            "typedef Objeto* (*Metodo )(...);\n" +
            "#define NUM_TO_PTR(N) ((PTR)(N))\n" +
            "#define PTR_TO_NUM(P) ((Num)(P))\n" +
            "#define STRING_TO_PTR(S) ((PTR)(S))\n" +
            "#define PTR_TO_STRING(P) ((String)(P))\n" +
            "#define METHOD_TO_PTR(M) ((PTR)(M))\n" +
            "#define PTR_TO_METHOD(P) ((Metodo)(P))\n" +
            "#define OBJECT_TO_PTR(O) ((PTR)(O))\n" +
            "#define PTR_TO_OBJECT(P) ((Objeto*)(P)) \n";


    public static String getHeader() {
        return header;
    }



    /*
   * Método que armará las definiciones de clases
   * Ej:
   *  Clase* cls2 ;
   *  Clase* cls3 ;
   **/
    public static String getClassesHeader(List<CClass> cClasses) {
        String classes = "";
        for (CClass cl : cClasses){
            classes+= "Clase* "+cl.getID()+";\n";
        }
        return classes;
    }


    /*
    * compilar todos los métodos de una clase.
    * */
    public static String compileMethods(Class aClass, List<CClass> cClasses, List<CSelector> cSelectors, HashMap<String, String[]> table) {
        List<Method> methods = aClass.getMethods();
        String compiledMethods = "";
        String cclass = Collector.getCClassName(cClasses, aClass.getId());
        for (Method m : methods){
            compiledMethods += CodeHelper.compileMethod(aClass, m, cclass,  cSelectors, table);
        }
        Environment.clean();
        return compiledMethods;
    }

    /*
    * Compilar un método
    * */
    private static String compileMethod(Class aClass, Method m, String cclass, List<CSelector> cSelectors, HashMap<String, String[]> table) {
        Environment.clean();
        String declaration = "/*"+cclass+ "=>"+aClass.getId()+"," +m.getId()+"*/\n";
        declaration+= CodeHelper.getMethodDeclaration(m, cclass, cSelectors);
        declaration+= CodeHelper.getParameters(m.getParameters());
        declaration += "{\n";
        declaration += CodeHelper.compileExpressions(aClass, m, cclass);
        declaration += "}\n";
        return declaration;
    }

    /*
    * Compilar todas las expresiones de un bloque
    * */
    private static String compileExpressions(Class aClass, Method method, String cclass) {
        String block = "";
        Boolean lastLine = false;
        String compiled = "";
        String check = "";
        for (int i = 0; i < method.getBlock().size(); i++) {
            if (i == method.getBlock().size()-1){
                lastLine = true;
            }
            Expression e = method.getBlock().get(i);
            compiled = e.compile(method, aClass, cclass, lastLine);
            if (compiled.startsWith("PTR_TO_METHOD")){
                check = CodeHelper.generateChecker(compiled, (Send) e);
            }
            block += check+compiled;
            block += "; \n";
        }
        return Environment.getEnv()+" \n "+modifyLastLine(block);
    }


    /*
    * genera los chequeos para ver si un método está implementado
    * */
    public static String generateChecker(String assignment, Send send) {
        String msj = send.getID() + "/" +send.getArguments().size();
        String met = assignment.substring(14);
        met = met.substring(0, met.indexOf(']')+1);
        String ret = "if ("+met+" == NULL) {\n" +
                "   fprintf(stderr,\"El objeto no acepta el mensaje ’"+msj+"’.\\n\");\n" +
                "   exit(1);\n" +
                "}\n";
        return ret;
    }


    /*
    * Hack para agregar return en la última línea-
    * */
    private static String modifyLastLine(String block) {
        String[] split = block.split("\n");
        split[split.length-1] = "return " +split[split.length-1];
        String ret = "";
        for (int i = 0; i < split.length; i++) {
            ret += split[i] + "\n";
        }
        return ret;
    }


    /*Método que calcula los parámetros de un método*/
    private static String getParameters(List<String> parameters) {
        String param = "(Objeto* o0";
        int counter = 1;
        for (String p : parameters){
            param+= ", Objeto* o"+counter;
            counter++;
        }
        param += ")";
        return param;
    }

    /*Método que genera la declaración de un método*/
    private static String getMethodDeclaration(Method m, String cclass, List<CSelector> cSelectors) {
        String selectorId = Collector.getSelectorId(m, cSelectors);

        return "Objeto* met_"+cclass+"_"+selectorId;
    }


    /*
    * Método que inicializa las clases y sus punteros en el prgrama principal
    * */
    public static String getInitialization(String[] strings, String c, int methodsSize) {
        String ret = "    "+c +" = new Clase;\n";
        String size = String.valueOf(Integer.valueOf(methodsSize));
        ret += "    "+c+"->metodos = new PTR ["+size+"];\n";
        for (int i = 0; i < methodsSize; i++) {
            if (strings[i]== null){
                ret+= "    "+c+"->metodos["+i+"] = NULL;\n";
            }
            else {
                ret+= "    "+c+"->metodos["+i+"] = METHOD_TO_PTR(met_"+c+"_"+strings[i]+");\n";
            }
        }
        return ret;
    }

    /*
    * Inicialización de las clases nativas
    * */
    public static String getNativeInitializations(int methodsSize) {
        String ret = "    /* Inicialización de la clase cls0 ( Int ) */\n" +
                "    cls0 = new Clase;\n" +
                "    cls0->metodos = new PTR[7];\n" +
                "    cls0->metodos[0] = METHOD_TO_PTR(met_cls0_sel0); /* print/0 */\n" +
                "    cls0->metodos[1] = METHOD_TO_PTR(met_cls0_sel1); /* add/1 */\n" ;
        for (int i = 2; i < methodsSize; i++) {
            ret += "    cls0->metodos["+i+"] = NULL;\n";
        }
        ret+=   "    /* Inicialización de la clase cls1 ( String ) */\n" +
                "    cls1 = new Clase;\n" +
                "    cls1->metodos = new PTR[7];\n" +
                "    cls1->metodos[0] = METHOD_TO_PTR(met_cls1_sel0); /* print/0 */\n" +
                "    cls1->metodos[1] = METHOD_TO_PTR(met_cls1_sel1); /* add/1 */\n";
        for (int i = 2; i < methodsSize; i++) {
            ret += "    cls1->metodos["+i+"] = NULL;\n";
        }

        return ret;
    }

    /*
    * Definición de las clases nativas
    * */
    public static String getNativeClassesDef (){
        return "/* Construye un objeto de clase Int */\n" +
                "Objeto* constructor_cls0 (Num valor) {\n" +
                "   Objeto* obj = new Objeto ;\n" +
                "   obj -> clase = cls0 ; /* Int */\n" +
                "   obj -> varsInstancia = new PTR [1];\n" +
                "   obj -> varsInstancia[0] = NUM_TO_PTR (valor);\n" +
                "   return obj ;\n" +
                " }\n" +
                "/* Construye un objeto de clase String */\n" +
                "Objeto* constructor_cls1 ( String valor ) {\n" +
                "   Objeto* obj = new Objeto ;\n" +
                "   obj -> clase = cls1 ; /* String */\n" +
                "   obj -> varsInstancia = new PTR [1];\n" +
                "   obj -> varsInstancia[0] = STRING_TO_PTR (valor);\n" +
                "   return obj ;\n" +
                "}\n" +
                "Objeto* met_cls0_sel0(Objeto* o0) {\n" +
                "  cout << PTR_TO_NUM(o0->varsInstancia[0]) << endl;\n" +
                "  return o0;\n" +
                "}\n" +
                "\n" +
                "Objeto* met_cls0_sel1(Objeto* o0, Objeto* o1) {\n" +
                "  Num n1 = PTR_TO_NUM(o0->varsInstancia[0]);\n" +
                "  Num n2 = PTR_TO_NUM(o1->varsInstancia[0]);\n" +
                "  return constructor_cls0(n1 + n2);\n" +
                "}\n" +
                "\n" +
                "Objeto* met_cls1_sel0(Objeto* o0) {\n" +
                "  cout << PTR_TO_STRING(o0->varsInstancia[0]) << endl;\n" +
                "  return o0;\n" +
                "}\n" +
                "\n" +
                "Objeto* met_cls1_sel1(Objeto* o0, Objeto* o1) {\n" +
                "  String s1 = PTR_TO_STRING(o0->varsInstancia[0]);\n" +
                "  String s2 = PTR_TO_STRING(o1->varsInstancia[0]);\n" +
                "  string str1(s1);\n" +
                "  string str2(s2);\n" +
                "  const char* result = (str1 + str2).c_str();\n" +
                "  return constructor_cls1(result);\n" +
                "}\n";
    }

    /*
    * Genera la construcción de una clase
    * */
    public static String getClassConstructor(Class cl, String id) {
        String varsInstancia = "";
        for (int i = 0; i < cl.localsSize(); i++) {
            varsInstancia +="   obj -> varsInstancia["+i+"] = constructor_cls0 (0);\n";
        }
        String constructor = "Objeto* constructor_"+id+"() {\n" +
                "   Objeto* obj = new Objeto;\n" +
                "   obj -> clase = "+id+";\n" +
                "   obj -> varsInstancia = new PTR ["+cl.localsSize()+"];\n"
                +varsInstancia+
                "   return obj;\n" +
                "} ";
        return constructor;
    }

    /*
    * Genera el código para ejcutra el método main de la clase Main
    * */
    public static String executeMain() {
        String ret = "";
        String cmain = Collector.getCClassName(Compiler.cClasses, "Main");
        String selector = Collector.getSelectorIdByMessage("main", 0, Compiler.cSelectors);
        int nbr = Integer.parseInt(selector.substring(3));

        ret += "    PTR_TO_METHOD(constructor_"+cmain+"()->clase->metodos["+nbr+"])("+cmain+"); \n";

        return ret;
    }
}
