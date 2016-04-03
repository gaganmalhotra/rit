package crackingTheCoding;

import java.util.HashMap;

/**
 * This class contains character and children nodes
 */
class TrieNode{
	char c;
	HashMap<Character, TrieNode> child = new HashMap<Character, TrieNode>();
	boolean isLeaf=false;
	
	//constructor with char ip
	TrieNode(char c){
		this.c=c;
	}
	
	//empty constructor
	TrieNode(){
	}
}


public class Trie_Implementation {
	//Class var to hold the root of tree
	TrieNode root;
	
	//constructor
	public Trie_Implementation() {
		root=new TrieNode();
	}
	
//-------------------------------------------------------------------------------------//--------------------
	//insert method to insert a string into trie data structure
	public void insert(String word){
		//first get all the children of the root
		HashMap<Character,TrieNode> children= root.child;
		
		//now iterate through each charcter of th string
		for (int i = 0; i < word.length(); i++) {
			char ch= word.charAt(i);
			
			TrieNode t; // this will either hold the existing node or new node
			
			if(children.containsKey(ch)){
				t=children.get(ch); //set t to existing node
			}else{
				t= new TrieNode(ch); //make new node with that char
				children.put(ch, t);
			}
			children=t.child; //set children to either existing node's children or new node's
			
			// if leaf node set isLeaf true
			if(i==word.length()-1){
				t.isLeaf=true;
			}else if(t.isLeaf){
				t.isLeaf=false; //suppose a scenario where 'in' is already inserted and we insert 'inn' now
								// so the 2nd char 'n' which was marked previously as leaf will now be marked leaf=false
								// and now 3rd char 'n' will be marked as leaf
			}
		}
	}

//-------------------------------------------------------------------------------------//-------------
	
	//This method returns the last character node of the search string
	public TrieNode searchNode(String search){
		
		HashMap<Character, TrieNode> children = root.child;
		TrieNode returnNode = null;
		
		for (int i = 0; i < search.length(); i++) {
			char ch= search.charAt(i);
			
			if(children.containsKey(ch)){
				returnNode=children.get(ch);
				children=returnNode.child;
			}else{
				return null;
			}
		}
		return returnNode;
	}
	
//-------------------------------------------------------------------------------------//-------------
	
	//this method searches for a given word in the trie and returns true or false
	public boolean search(String str){
		TrieNode t= searchNode(str);
		if(t!=null && t.isLeaf==true){
			return true;
		}else{
			return false;
		}
	}

//-------------------------------------------------------------------------------------//-------------
	
	//this method finds if there is any word present in the trie with the given prefix
	public boolean startsWith(String prefix){
		TrieNode t= searchNode(prefix);
		if(t==null){
			return false;
		}else{
			return true;
		}
	}
	
	
	
	
}
