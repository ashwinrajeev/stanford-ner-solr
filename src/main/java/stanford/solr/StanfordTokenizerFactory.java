package stanford.solr;

import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class StanfordTokenizerFactory extends TokenizerFactory {

	private String classifier;

	public StanfordTokenizerFactory(Map<String, String> args) {
		super(args);
		assureMatchVersion();
		if (args.containsKey("classifier")) {
			classifier = args.get("classifier");
		} else {
            classifier = "/usr/share/dse/resources/solr/lib/classifiers/english.all.3class.distsim.crf.ser.gz";
			System.err.println("we need model dir for fnlp");
		}
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader in) {
		return new StanfordTokenizer(in, classifier);
	}

	public String getModelDir() {
		return classifier;
	}

	public void setModelDir(String classifier) {
		this.classifier = classifier;
	}
}
