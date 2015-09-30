package stanford.solr;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;


public class StanfordAdapter implements Iterator<String> {


    private static StanfordAdapter adapter;
    private static String text;
    private Iterator<java.lang.String> tokens;
    private CRFClassifier<CoreLabel> segmenter;

    private StanfordAdapter(Reader input, String classifier) {
//         StanfordAdapter.class.getResource("/english.all.3class.distsim.crf.ser.gz" ).getFile()
        segmenter = CRFClassifier.getClassifierNoExceptions(classifier);
    }

    public static StanfordAdapter getInstance(Reader input, String classifier) {
        if (null == adapter) {
            adapter = new StanfordAdapter(input, classifier);
        }
        return adapter;
    }

    public void reset(Reader input) throws IOException {
        int intValueOfChar;
        String text = "";
        while ((intValueOfChar = input.read()) != -1) {
            text += (char) intValueOfChar;
        }
        List<String> nouns =  new ArrayList<String>();
        for(Triple<java.lang.String,java.lang.Integer,java.lang.Integer> token: segmenter.classifyToCharacterOffsets(text)){
            nouns.add(token.first + ":" + text.substring(token.second, token.third));
        }
        tokens = nouns.iterator();
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNext();
    }

    @Override
    public String next() {
        return tokens.next();
    }

}
