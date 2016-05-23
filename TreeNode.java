public class TreeNode<T> {
  protected TreeNode left;
  protected TreeNode right;
  protected TreeNode parent;
  protected T data;
 
  // Constructor initializes data members.
  // Target Complexity: O(1)
  public TreeNode(T data,TreeNode left,TreeNode right,TreeNode parent)
  {
	  this.left = left;
	  this.right = right;
	  this.parent = parent;
	  this.data = data;
  }
 
  // Returns a pretty representation of the node.
  // Format: [data]. Example: [3]
  // Target Complexity: O(1)
  public String toString()
  {
	  return "[" + data + "]";
  }
 
  // Returns a pretty representation of the node for debugging.
  // Shows the data values of this node and its left, right, and parent 
  // nodes. Format:[D:data,L:left,R:right,P:parent]. 
  // Example:[D:3, L:1, R:2, P:0]
  // Target Complexity: O(1)
  public String debugToString()
  {
	  return "[D:" + data + ", L:" + left +", R:" + right +", P:" + parent + "]"; 
  }
  
} 