package nl.tue.vmcourse.toy.builtins;

import java.io.FileWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Interval;

import nl.tue.vmcourse.toy.interpreter.ToyAbstractFunctionBody;
import nl.tue.vmcourse.toy.interpreter.ToyNodeFactory;
import nl.tue.vmcourse.toy.lang.RootCallTarget;
import nl.tue.vmcourse.toy.lang.VirtualFrame;
import nl.tue.vmcourse.toy.parser.ToyLangLexer;
import nl.tue.vmcourse.toy.parser.ToyLangParser;

public class EvalBuiltin extends ToyAbstractFunctionBody {
    private final Map<String, RootCallTarget> allFunctions;

    public EvalBuiltin(Map<String, RootCallTarget> allFunctions) {
        this.allFunctions = allFunctions;
    }

    public Object invoke(Object id, Object code) {
        String key = (String)id;
        String src = (String)code;

        if(!key.equals("sl")) {
            throw new RuntimeException("Eval function not supported for " + key + " language");
        }

        CharStream charStream = CharStreams.fromString(src);
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
        allFunctions.putAll(factory.getAllFunctions());
        return null;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        return this.invoke(args[0], args[1]);
    }
    
}
