import java.util.*;
import java.io.*;

class Node {

	private Node next;
	private String state;

	public Node(String state) {

		this.state = state;
	}

	public String getState() {

		return state;
	}

	public void setState(String state) {

		this.state = state;
	}

	public Node getNext() {

		return next;
	}

	public void setNext(Node next) {

		this.next = next;
	}
}