class Blood {
  ArrayList<Drop> drops;
  
  public Blood(int x, int y) {
    drops = new ArrayList<Drop>();
    drops.add(new Drop(x, y));
  }
}
