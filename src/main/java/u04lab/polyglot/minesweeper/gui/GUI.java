package u04lab.polyglot.minesweeper.gui;

import u04lab.polyglot.Pair;
import u04lab.polyglot.minesweeper.logic.Logics;
import u04lab.polyglot.minesweeper.logic.LogicsImpl;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size, int bombs) {
        this.logics = new LogicsImpl(size, bombs);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setEnabled(false);
            final Pair<Integer,Integer> pos = buttons.get(bt);
            boolean aMineWasFound = logics.checkIfContainsBomb(pos.getX(), pos.getY()); // call the logic here to tell it that cell at 'pos' has been seleced
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
                System.exit(0);
            } else {
                drawBoard();            	
            }
            boolean isThereVictory = logics.won(); // call the logic here to ask if there is victory
            if (isThereVictory){
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    // call the logic here to put/remove a flag
                    logics.changeFlag(pos.getX(), pos.getY());
                }
                drawBoard(); 
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }
    
    private void quitGame() {
        logics.revealAllBombs();
        this.drawBoard();
    	for (var entry: this.buttons.entrySet()) {
            // call the logic here
            // if this button is a mine, draw it "*"
            // disable the button
            var pair = entry.getValue();
            if (this.logics.getStatus(pair.getX(), pair.getY()).equals(RenderStatus.BOMB)) {
                entry.getKey().setEnabled(false);
            }
    	}
    }

    private void drawBoard() {
        for (var entry: this.buttons.entrySet()) {
            // call the logic here
            // if this button is a cell with counter, put the number
            // if this button has a flag, put the flag
            var status = logics.getStatus(entry.getValue().getX(), entry.getValue().getY());
            var render = switch (status) {
                case BOMB -> "*";
                case COUNTER -> String.valueOf(status.getCounter());
                case FLAG -> "F";
                case HIDDEN -> "";
            };
            entry.getKey().setText(render);
            if (status.equals(RenderStatus.COUNTER)) {
                entry.getKey().setEnabled(false);
            }
    	}
    }
    
}
