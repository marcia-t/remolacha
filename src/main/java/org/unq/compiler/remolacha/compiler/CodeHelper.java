package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.grammar.Class;

import java.util.List;

/**
 * Created by mtejeda on 14/11/17.
 */
public class CodeHelper {

    private static final String header =
            "#include <cstdlib>\n" +
            "#include <cstdio>\n" +
            "typedef unsigned long long int Num;\n" +
            "typedef char* String;\n" +
            "typedef void* PTR;\n" +
            "struct Clase {\n" +
            "   PTR* metodos;\n" +
            "};\n" +
            "struct Objeto {\n" +
            "   Clase* clase;\n" +
            "   PTR* varsInstancia;\n" +
            "};\n" +
            "//typedef Objeto* (*Metodo )(...);\n" +
            "#define NUM_TO_PTR(N) ((PTR)(N))\n" +
            "#define PTR_TO_NUM (P) ((Num)(P))\n" +
            "#define STRING_TO_PTR (S) ((PTR)(S))\n" +
            "#define PTR_TO_STRING (P) ((String)(P))\n" +
            "#define METHOD_TO_PTR (M) ((PTR)(M))\n" +
            "#define PTR_TO_METHOD (P) ((Metodo)(P))\n" +
            "#define OBJECT_TO_PTR (O) ((PTR)(O))\n" +
            "#define PTR_TO_OBJECT (P) ((Objeto*)(P)) \n";


    public static String getHeader() {
        return header;
    }

    public static void print(){
        System.out.println(getHeader());
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

    public static String getNativeClassesDef (){
        return "/* Construye un objeto de clase Int */\n" +
                "Objeto* constructor_cls0 ( Num valor ) {\n" +
                "   Objeto* obj = new Objeto ;\n" +
                "   obj - > clase = cls0 ; /* Int */\n" +
                "   obj - > varsInstancia = new PTR [1];\n" +
                "   obj - > varsInstancia[0] = NUM_TO_PTR (valor);\n" +
                "   return obj ;\n" +
                " }\n" +
                "/* Construye un objeto de clase String */\n" +
                "Objeto* constructor_cls1 ( String valor ) {\n" +
                "   Objeto* obj = new Objeto ;\n" +
                "   obj - > clase = cls1 ; /* String */\n" +
                "   obj - > varsInstancia = new PTR [1];\n" +
                "   obj - > varsInstancia[0] = STRING_TO_PTR (valor);\n" +
                "   return obj ;\n" +
                "}\n";
    }

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
}
