package stanford.solr;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;

public class StanfordTokenizer extends Tokenizer {

    private StanfordAdapter adapter;

    private CharTermAttribute termAtt;
    private boolean ready;
    /**
     * @param input
     */
    public StanfordTokenizer(Reader input, String classifier) {
        super(input);
        this.termAtt = addAttribute(CharTermAttribute.class);
        adapter = StanfordAdapter.getInstance(input, classifier);
        ready = false;
    }

    /**
     * @param factory
     * @param input
     */
    public StanfordTokenizer(AttributeFactory factory, Reader input) {
        super(factory, input);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        clearAttributes();
        if (!ready)
            throw new IllegalStateException();
        if(adapter.hasNext()) {
            String token = adapter.next();
            termAtt.setEmpty().append(token);
            termAtt.setLength(token.length());
            return true;
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        adapter.reset(this.input);
        ready = true;
    }
}
