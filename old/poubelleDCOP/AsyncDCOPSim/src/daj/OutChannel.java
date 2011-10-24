// ----------------------------------------------------------------------------
// $Id: OutChannel.java,v 1.2 1997/10/22 20:52:10 ws Exp $
// channels to send messages to
//
// (c) 1997, Wolfgang Schreiner <Wolfgang.Schreiner@risc.uni-linz.ac.at>
// http://www.risc.uni-linz.ac.at/software/daj
// ----------------------------------------------------------------------------
package daj;

public interface OutChannel {

	// --------------------------------------------------------------------------
	// send `msg` to channel
	// --------------------------------------------------------------------------
	public void send(Message msg);
}
