/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore;

import java.util.Stack;

/**
 *
 * @author DELL
 */
public class CareTaker {
    
    private Stack<Memento> mementosStack;

    public CareTaker() {
        this.mementosStack = new Stack<>();
    }
    
    Memento popLastMemento(){
        return mementosStack.pop();
    }
    
    void addMemento(Memento memento){
        mementosStack.push(memento);
    }
    
}
