package cle.dao;

import com.fasterxml.jackson.module.hibernate.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

public class HibernateAwareObjectMapper extends ObjectMapper {
	 public HibernateAwareObjectMapper() {
		   HibernateModule hm = new HibernateModule();
		   registerModule(hm);
		   configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		 }

		 public void setPrettyPrint(boolean prettyPrint) {
		   configure(Feature.INDENT_OUTPUT, prettyPrint);
		 }
}
