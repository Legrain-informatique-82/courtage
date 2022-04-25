package fr.legrain.bugzilla.rest.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;

import fr.legrain.bugzilla.rest.util.BugzillaUtil;
import fr.legrain.lib.data.LibDate;

public class ResultCommentSearchDeserializer extends StdDeserializer<ResultCommentSearch> { 

	private static final long serialVersionUID = 5175282915984270835L;

	public ResultCommentSearchDeserializer() { 
        this(null); 
    } 
 
    public ResultCommentSearchDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public ResultCommentSearch deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        Iterator<JsonNode> i =  node.get("bugs").elements().next().get("comments").elements();
//        int taille = ((ArrayNode)node.get("bugs").elements().next().get("comments")).size();
        List<Comment> l = new LinkedList<>();
        
        while (i.hasNext()) {
			JsonNode jsonNode = (JsonNode) i.next();
			Comment c = new Comment();
			c.setId( ((IntNode)jsonNode.get("id")).intValue());
			c.setBugId( ((IntNode)jsonNode.get("bug_id")).intValue());
			c.setCount( ((IntNode)jsonNode.get("count")).intValue());
			c.setText( ((TextNode)jsonNode.get("text")).textValue());
			c.setIsPrivate( ((BooleanNode)jsonNode.get("is_private")).booleanValue());
			c.setCreator( ((TextNode)jsonNode.get("creator")).textValue());
			if(!(jsonNode.get("attachment_id") instanceof NullNode)) {
				c.setAttachmentId( ((IntNode)jsonNode.get("attachment_id")).intValue());
			}
			c.setCreationTime(((TextNode)jsonNode.get("creation_time")).textValue());
			c.setCreationTime(BugzillaUtil.bugzillaStringDateToString(c.getCreationTime()));
			
			c.setText(c.getText().replaceAll("\n", "<br/>"));
			l.add(c);
		}
//        for (int j = 0; j < taille; j++) {
//        	JsonNode jsonNode =((ArrayNode)node.get("bugs").elements().next().get("comments")).get(0);
//        	Comment c = new Comment();
//			c.setText( ((TextNode)jsonNode.get("text")).textValue());
//			l.add(c);
//		}
        
        
        ResultCommentSearch r = new ResultCommentSearch();
        r.setComments(l);

        return r;
    }
    
}