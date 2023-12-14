package cn.wjhub.gpt.common.util;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.List;

public interface Loader {

	List<Document> load();

	List<Document> load(TextSplitter textSplitter);

}