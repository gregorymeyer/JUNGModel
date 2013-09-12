package experiment;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class Main 
{
	public static void main(String[] args)
	{
		System.out.println("Hello there");
		Jungtest thisJung = new Jungtest();
		thisJung.go();
	}
		
}
