package dima.basiccommunicationcomponents;

/**
 * Insert the type's description here.
 * Creation date: (01/03/2000 23:51:58)
 * @author: Gerard Rozsavolgyi
 */
 import java.io.Serializable;

import dima.basicagentcomponents.AgentIdentifier;
import dima.basicagentcomponents.AgentName;
import dima.kernel.communicatingAgent.BasicCommunicatingAgent;





public class Message extends AbstractMessage implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8134195935509780896L;
	private AgentIdentifier sender;
	private String type ="java";
	private AgentIdentifier receiver;
	private Object[] args;
/**
 * Message constructor comment.
 */
public Message() {
	super();
	this.type = "java";
}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent) {
	this.content=msgContent;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent, final Serializable [] paras) {
	this.content=msgContent;
	this.args = paras;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent, final Object [] paras) {
	this.content=msgContent;
	this.args = paras;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */

/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent, final Object para) {
	this.content=msgContent;
	this.args = new Object [1];
	this.args [0]= para;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */

/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent, final Object para1, final Object para2) {
	this.content=msgContent;
	this.args = new Object [2];
	this.args [0]= para1;
	this.args [1]= para2;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
public Message(final String  msgContent, final Object para1, final Object para2, final Object para3) {
	this.content=msgContent;
	this.args = new Object [3];
	this.args [0]= para1;
	this.args [1]= para2;
	this.args [2]= para3;
	this.type = "java";
	}
/**
 * Insert the method's description here.
 * Creation date: (07/05/00 09:51:51)
 * @param status java.lang.String
 * @param msgContent org.omg.CORBA.Object
 * @param msgReceiver Gdima.kernel.AgentAddress
 */
@Override
public boolean equals(final Object obj) {
	// Insert code to compare the receiver and obj here.
	// This implementation forwards the message to super.  You may replace or supplement this.
	// NOTE: obj might be an instance of any class
	return super.equals(obj);
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @return java.lang.Object[]
 */
public java.lang.Object[] getArgs() {
	return this.args;
}
/**
 * getContent method comment.
 */
@Override
public Object getContent() {
	return this.content;
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @return Gdima.basicagentcomponents.AgentIdentifier
 */
@Override
public dima.basicagentcomponents.AgentIdentifier getReceiver() {
	return this.receiver;
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @return Gdima.basicagentcomponents.AgentIdentifier
 */
@Override
public dima.basicagentcomponents.AgentIdentifier getSender() {
	return this.sender;
}
/**
 * Insert the method's description here.
 * Creation date: (07/07/2002 10:57:16)
 * @return java.lang.String
 */
public java.lang.String getType() {
	return this.type;
}
/**
 * Generates a hash code for the receiver.
 * This method is supported primarily for
 * hash tables, such as those provided in java.util.
 * @return an integer hash code for the receiver
 * @see java.util.Hashtable
 */
@Override
public int hashCode() {
	// Insert code to generate a hash code for the receiver here.
	// This implementation forwards the message to super.  You may replace or supplement this.
	// NOTE: if two objects are equal (equals(Object) returns true) they must have the same hash code
	return super.hashCode();
}
/**
 * Insert the method's description here.
 * Creation date: (27/04/00 12:28:43)
 */


public Object process(final BasicCommunicatingAgent a)
{
	//System.out.println("Traitement du message" + "  "+ sender+ "   "+ receiver + "  "+ (String)getContent());
	return MessageSend2.invoke(a,(String)this.getContent(), this.args);

	}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @param newArgs java.lang.Object[]
 */
public void setArgs(final java.lang.Object[] newArgs) {
	this.args = newArgs;
}
/**
 * setContent method comment.
 */
@Override
public void setContent(final Object o)
{
	this.content = o;
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @param newReceiver Gdima.basicagentcomponents.AgentIdentifier
 */
@Override
public void setReceiver(final dima.basicagentcomponents.AgentIdentifier newReceiver) {
	this.receiver = newReceiver;
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @param newReceiver Gdima.basicagentcomponents.AgentIdentifier
 */
public void setReceiver(final String newReceiver) {
	this.receiver = new AgentName(newReceiver);
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @param newSender Gdima.basicagentcomponents.AgentIdentifier
 */
@Override
public void setSender(final dima.basicagentcomponents.AgentIdentifier newSender) {
	this.sender = newSender;
}
/**
 * Insert the method's description here.
 * Creation date: (23/05/01 16:02:47)
 * @param newSender Gdima.basicagentcomponents.AgentIdentifier
 */
public void setSender(final String newSender) {
	this.sender = new AgentName(newSender);
}
/**
 * Insert the method's description here.
 * Creation date: (07/07/2002 10:57:16)
 * @param newType java.lang.String
 */
public void setType(final java.lang.String newType) {
	this.type = newType;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public String toString() {
	// Insert code to print the receiver here.
	// This implementation forwards the message to super. You may replace or supplement this.
	return super.toString();

	}
}
