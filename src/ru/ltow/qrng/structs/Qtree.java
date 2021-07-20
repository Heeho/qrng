package ru.ltow.qrng;

import java.util.ArrayList;

public class Qtree {
  private static int capacity = 4;

  private static final int 
  BASE = 4,
  NW = 0,
  SW = 1,
  SE = 2,
  NE = 3;

  private static int depthmax;
  private int depth;

  private ArrayList<Dot> dots = new ArrayList<>();
  private AABB boundary;
  private Qtree[] child;

  public Qtree(AABB b) {
    boundary = b;
  }

  public void depth(int d) {depth = d;}
  public static void capacity(int c) {capacity = c;}

  public boolean empty() {return dots.isEmpty();}
  public boolean full() {return dots.size() == capacity;}
  public boolean childfree() {return child == null;}

  public static int capacity() {return capacity;}
  public int depth() {return depth;}
  public ArrayList<Dot> dots() {return dots;}
  public AABB boundary() {return boundary;}

  public ArrayList<Qtree> nodes(int dmin) {
    ArrayList<Qtree> nodes = new ArrayList<>();

    if(depth >= dmin) nodes.add(this);

    if(child != null) {
      for(Qtree q : child) nodes.addAll(q.nodes(dmin));
    }

    return nodes;
  }

  public boolean insert(Dot dot) {
    if(!boundary.contains(dot)) return false;

    if(dots.size() < capacity && child == null) {
      dots.add(dot);
      return true;
    } else {
      if(child == null) child();
      for(Qtree q : child) {
        if(q.insert(dot)) return true;
      }
    }

    return false;
  }

  //the deepest most populated leaf node
  public Qtree cluster() {
    if(child == null) return this;

    Qtree cluster = null, leaf = null;
    int leafdepth = 0, leafdots = 0;

    for(Qtree q : child) {
      leaf = q.cluster();
      if(leaf.depth > leafdepth) {
        leafdepth = leaf.depth;
        leafdots = leaf.dots.size();
        cluster = leaf;
      } else if(leaf.depth == leafdepth && leaf.dots.size() > leafdots) {
        leafdots = leaf.dots.size();
        cluster = leaf;
      }
    }

    return cluster;
  }

  //the shallowest least populated leaf node
  public Qtree hole() {
    if(child == null) return this;

    Qtree hole = null, leaf = null;
    int leafdepth = depthmax, leafdots = capacity;

    for(Qtree q : child) {
      leaf = q.hole();
      if(leaf.depth < leafdepth) {
        leafdepth = leaf.depth;
        leafdots = leaf.dots.size();
        hole = leaf;
      } else if(leaf.depth == leafdepth && leaf.dots.size() < leafdots) {
        leafdots = leaf.dots.size();
        hole = leaf;
      }
    }

    return hole;
  }

  private void child() {
    child = new Qtree[BASE];
    AABB[] boundaries = boundary.split();

    child[NW] = new Qtree(boundaries[NW]);
    child[SW] = new Qtree(boundaries[SW]);
    child[SE] = new Qtree(boundaries[SE]);
    child[NE] = new Qtree(boundaries[NE]);

    int newdepth = depth + 1;
    for(Qtree q : child) q.depth(newdepth);

    if(newdepth > depthmax) depthmax = newdepth;
  }
}