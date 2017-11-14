package org.unq.compiler.remolacha.compiler;

/**
 * Created by mtejeda on 14/11/17.
 */
public class CodeHelper {

    private final String header =
            "#include <cstdlib>\n" +
            "#include <cstdio>\n" +
            "typedef unsigned long long int Num ;\n" +
            "typedef char* String ;\n" +
            "typedef void* PTR ;\n" +
            "struct Clase {\n" +
            "   PTR* metodos ;\n" +
            "};\n" +
            "struct Objeto {\n" +
            "   Clase* clase ;\n" +
            "   PTR* varsInstancia ;\n" +
            "};\n" +
            "//typedef Objeto* (*Metodo )(...);\n" +
            "#define NUM_TO_PTR(N) ((PTR)(N))\n" +
            "#define PTR_TO_NUM (P) ((Num)(P))\n" +
            "#define STRING_TO_PTR (S) ((PTR)(S))\n" +
            "#define PTR_TO_STRING (P) ((String)(P))\n" +
            "#define METHOD_TO_PTR (M) ((PTR)(M))\n" +
            "#define PTR_TO_METHOD (P) ((Metodo)(P))\n" +
            "#define OBJECT_TO_PTR (O) ((PTR)(O))\n" +
            "#define PTR_TO_OBJECT (P) ((Objeto*)(P)) \n" +
            "Clase * cls0 ;\n" +
            "Clase * cls1 ;\n" +
            "/* Construye un objeto de clase Int */\n" +
            "Objeto * constructor_cls0 ( Num valor ) {\n" +
            "   Objeto * obj = new Objeto ;\n" +
            "   obj - > clase = cls0 ; /* Int */\n" +
            "   obj - > varsInstancia = new PTR [1];\n" +
            "   obj - > varsInstancia [0] = NUM_TO_PTR ( valor );\n" +
            "   return obj ;\n" +
            " }\n" +
            "/* Construye un objeto de clase String */\n" +
            "Objeto * constructor_cls1 ( String valor ) {\n" +
            "   Objeto * obj = new Objeto ;\n" +
            "   obj - > clase = cls1 ; /* String */\n" +
            "   obj - > varsInstancia = new PTR [1];\n" +
            "   obj - > varsInstancia [0] = STRING_TO_PTR ( valor );\n" +
            "   return obj ;\n" +
            "}";

    public String getHeader() {
        return header;
    }

    public void print(){
        System.out.println(this.getHeader());
    }
}
