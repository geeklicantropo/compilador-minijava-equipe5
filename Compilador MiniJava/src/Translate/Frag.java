package Translate;

import Frame.Frame;
import List.List;
import Tree.Stm;

public class Frag {
	public Frame frame;
    private List<Stm> treeStm;
        
    public Frag(Frame frame, Stm treeStm){
    	this.frame = frame;
        this.treeStm = new List<Stm>(treeStm, null);
    }//fim construtor

    public List<Stm> getBody() {
    	return treeStm;
    }

    public void setBody(List<Stm> linearize) {
    	treeStm = linearize;
    }
}