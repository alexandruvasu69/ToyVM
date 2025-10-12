package nl.tue.vmcourse.toy;

import nl.tue.vmcourse.toy.interpreter.ToySyntaxErrorException;
import nl.tue.vmcourse.toy.lang.FrameDescriptor;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.builtins.ArrayGetSizeBuiltin;
import nl.tue.vmcourse.toy.builtins.DefineFunctionBuiltin;
import nl.tue.vmcourse.toy.builtins.EvalBuiltin;
import nl.tue.vmcourse.toy.builtins.GetSizeBuiltin;
import nl.tue.vmcourse.toy.builtins.HasSizeBuiltin;
import nl.tue.vmcourse.toy.builtins.HelloEqualsWorldBuiltin;
import nl.tue.vmcourse.toy.builtins.IsInstanceBuiltin;
import nl.tue.vmcourse.toy.builtins.NanoTimeBuiltin;
import nl.tue.vmcourse.toy.builtins.NewObject;
import nl.tue.vmcourse.toy.builtins.PrintBuiltin;
import nl.tue.vmcourse.toy.builtins.StackTraceBuiltin;
import nl.tue.vmcourse.toy.builtins.TypeOfBuiltin;
import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.interpreter.ToyRootNode;
import nl.tue.vmcourse.toy.parser.ToyLangLexer;
import nl.tue.vmcourse.toy.parser.ToyLangParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;

import java.io.IOException;
import java.util.Map;

public class ToyLauncher {

    public static final boolean JIT_ENABLED = System.getProperty("toy.Jit") != null;
    public static final boolean IC_ENABLED;
    public static final boolean ROPES_ENABLED;
    public static final boolean ARRAYS_ENABLED;
    public static final boolean DUMP_AST = System.getProperty("toy.DumpAst") != null; 
    public static final boolean DUMP_BYTECODE = System.getProperty("toy.DumpBytecode") != null; 

    static {
        // In your final submission, you want to remove this (otherwise, tests may fail!)
        if (JIT_ENABLED) {
            System.out.println("Toy Jit enabled -- all other optimizations are enabled by default");
            IC_ENABLED = true;
            ROPES_ENABLED = true;
            ARRAYS_ENABLED = true;
        } else {
            IC_ENABLED = System.getProperty("toy.InlineCaches") != null;
            ROPES_ENABLED = System.getProperty("toy.StringRopes") != null;
            ARRAYS_ENABLED = System.getProperty("toy.ArrayStrategies") != null;

            if (IC_ENABLED) {
                System.out.println("Toy Inline Caches enabled");
            }
            if (ROPES_ENABLED) {
                System.out.println("Toy String Ropes enabled");
            }
            if (ARRAYS_ENABLED) {
                System.out.println("Toy Array Strategies enabled");
            }
        }
    }

    public static Object eval(String code) {
        return evalStream(CharStreams.fromString(code));
    }

    public static String parseReportErrors(String code) {
        CharStream charStream = CharStreams.fromString(code);
        String src = charStream.getText(Interval.of(0, charStream.size()));
        ToyLangLexer lex = new ToyLangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ToyLangParser parser = new ToyLangParser(tokens);
        ToyNodeFactory factory = new ToyNodeFactory(src);
        parser.setFactory(factory);
        lex.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        lex.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        try {
            parser.toylanguage();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return null;
    }

    private static Object evalStream(CharStream charStream) {
        String src = charStream.getText(Interval.of(0, charStream.size()));
        ToyLangLexer lex = new ToyLangLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ToyLangParser parser = new ToyLangParser(tokens);
        ToyNodeFactory factory = new ToyNodeFactory(src);
        parser.setFactory(factory);
        lex.removeErrorListeners();
        parser.removeErrorListeners();
        lex.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.addErrorListener(new ToyLangParser.BailoutErrorListener());
        parser.toylanguage();

        Map<String, RootCallTarget> allFunctions = factory.getAllFunctions();
        if (!allFunctions.isEmpty() && allFunctions.containsKey("main")) {
            RootCallTarget mainFunction = allFunctions.get("main");
            registerAllBuiltIns(allFunctions);
            try {
                return mainFunction.invoke();
            } catch(RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    private static void registerAllBuiltIns(Map<String, RootCallTarget> allFunctions) {
        registerBuiltin(allFunctions, new PrintBuiltin(), "println");
        registerBuiltin(allFunctions, new NewObject(), "new");
        registerBuiltin(allFunctions, new StackTraceBuiltin(), "stacktrace");
        registerBuiltin(allFunctions, new HelloEqualsWorldBuiltin(), "helloEqualsWorld");
        registerBuiltin(allFunctions, new ArrayGetSizeBuiltin(), "getSize");
        registerBuiltin(allFunctions, new TypeOfBuiltin(), "typeOf");
        registerBuiltin(allFunctions, new IsInstanceBuiltin(), "isInstance");
        registerBuiltin(allFunctions, new HasSizeBuiltin(), "hasSize");
        registerBuiltin(allFunctions, new GetSizeBuiltin(), "getSize");
        registerBuiltin(allFunctions, new NanoTimeBuiltin(), "nanoTime");
        registerBuiltin(allFunctions, new EvalBuiltin(allFunctions), "eval");
        registerBuiltin(allFunctions, new DefineFunctionBuiltin(allFunctions), "defineFunction");
    }

    private static void registerBuiltin(Map<String, RootCallTarget> allFunctions, ToyAbstractFunctionBody builtin, String functionName) {
        ToyRootNode rootNode = new ToyRootNode(FrameDescriptor.newBuilder().build(), builtin, functionName);
        allFunctions.put(rootNode.getFunctionName(), rootNode.getCallTarget());
    }

    public static void main(String[] args) throws IOException {
        // TODO: change this when you will need to provide more arguments
        if (args.length < 1) {
            System.out.println("Usage: toy [file]");
            System.exit(1);
        }
        // TODO, ignores other args for now.
        CharStream charStream = CharStreams.fromFileName(args[args.length - 1]);
        try {
            Object result = evalStream(charStream);
            if(result != null) {
                System.out.println(result);
            }
        } catch (ToySyntaxErrorException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
