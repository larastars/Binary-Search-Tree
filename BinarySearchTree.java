import java.util.ArrayList;
import java.util.List;
import java.util.*;

//A Binary search tree that contains many non recursive methods
//this class extends comparable 
public class BinarySearchTree<T extends Comparable<? super T>> {
 protected TreeNode<T> root;        // root of tree
 protected int size;                // size of tree
 protected enum PrePostIn {PREORDER, POSTORDER, INORDER}; //contains traversals 
 
 // Constructor
 // Initialize variables – root is null and size is 0.
 public BinarySearchTree( )
 {
	  root = null;
	  size =0;
 }
 
 // Constructor that builds a tree from the values in sorted List L.
 // Initialize variables – root is null and size is 0 - and call 
 // recursive method buildTree() if L.size() > 0.
 // Throws IllegalArgumentException if L is null or any element in L is 
 // null.
 public BinarySearchTree(List<T> L)
 {
	  root = new TreeNode<T>(null,null,null,null);
	  size = L.size();
	  if (L.size() == 0)
	  {
		  throw new IllegalArgumentException("Error! the list is empty");
	  }
	  //make sure that the list does not have empty data 
	  for (int i=0 ; i < L.size(); i++)
	  {
		   if (L.get(i) ==  null)
		   {
		    throw new IllegalArgumentException("Error! element in the list is null");
		   }
	  }
	  if(L.size() > 0)
	  {
		  root =  buildTree(0, L.size()-1, L); 
	  }
 }
    
 // Builds a balanced tree from the values in sorted List L.
 // Start and end are the start and end positions of a sub-list of L.
 // Returns the root of the subtree containing the elements in the 
 // sub-list of L.
 // Throws IllegalArgumentException if L or any element in L is null.
 // Called by BinarySearchTree(List<T> L) and balance().
 // This is a recursive method.
 // Target Complexity: O(1)
 @SuppressWarnings("unchecked")
 protected TreeNode<T> buildTree(int start, int end, List<T> L)
 {
	 //base condition 
	  if(start-1 >= end)
	   return null;
	  //find the middle 
	  int mid = middle(start,end);
	  //create a temporary node 
	  TreeNode<T> temp = new TreeNode<T>(null,null,null,null);
	  temp.data = L.get(mid);
	  TreeNode<T> leftChild = new TreeNode<T>(null,null,null,null);
	  //pass left child 
	  leftChild = this.buildTree(start,mid-1,L);
	  temp.left = leftChild;
	  if(leftChild != null)
	  { 
		  leftChild.parent = temp;
	  }
	  TreeNode<T> rightChild = new TreeNode<T>(null,null,null,null);
	  //pass right child 
	  rightChild = this.buildTree(mid+1, end, L);
	  temp.right = rightChild;
	  if(rightChild != null)
	  {
		  rightChild.parent = temp;
	  }
	  return temp;
 }
 
 // If x is not already in the tree, inserts x and returns true.
 // If x is already in the tree, does not insert x and returns false.
 // Sets the new nodes left, right, and parent references. 
 // On the first call to this method, start and end are the positions at 
 // the beginning and end of List L.
 // Throws IllegalArgumentException if x is null.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
 public boolean insert(T x)
 {
	  if (x == null)
	  {
		 throw new IllegalArgumentException("Error! x is null");
	  }
	
	  if (root == null)
	  {
		  //add the element to the root 
		  root = new TreeNode<T>(x,null,null,null);
		  size++;
		  return true;
	  }
	  //if x is already there then return false 
	  if (search(x) != null)
	  {
		  return false;
	  }
	  TreeNode <T> current = root;
	 int control = 1;
	  while (control == 1)
	  {
		  //if current > x
		   if (current.data.compareTo(x) > 0)
		   {
			   //go to the left of tree 
			    if (current.left != null)
			    {
			    	current = current.left;
			    }
			    else 
			    {
			    	//add new node 
				     TreeNode <T> newNode = new TreeNode<T>(x,null,null,current);
				     current.left = newNode;
				     size++;
				     return true;
			    } 
			   }
			   else //go to the right of the tree
			   {
				    if (current.right != null)
				    {
				    	current = current.right;
				    }
				    else
				    {
				    	//add new node 
					     TreeNode <T> newNode = new TreeNode<T>(x,null,null,current);
					     current.right = newNode;
					     size++;
					     return true;
				    } 
		   } 
	  }
	  return false;
 }
 //helper method that determines if a node is a leaf Node
 private boolean isLeaf(TreeNode <T> node)
 {
	 return (node.left == null && node.right == null && node.parent != null);
 }
 //helper method that determines if a node has one child
 private boolean hasOneChild(TreeNode <T> node)
 {
	 return ((node.left == null && node.right != null) || 
			 node.left != null && node.right == null);
 }
 //helper method that finds the minimum value in the subtree
 @SuppressWarnings("unchecked")
private TreeNode <T> findMinValue(TreeNode <T> node)
 {
	 while (node.left != null)
	 {
		 node = node.left;
	 }
	 return node;
 }
//helper method that finds the max value in the subtree
 @SuppressWarnings("unchecked")
private TreeNode <T> findMaxValue(TreeNode <T> node)
 {
	 while (node.right != null)
	 {
		 node = node.right;
	 }
	 return node;
 }
 //helper method that removes the leaf node 
 @SuppressWarnings("unchecked")
private boolean removeLeafNode (TreeNode <T>remNode)
 {
	//compare with parents
	//if > then set parents right to null
	//else set parents left to null 
	TreeNode <T> parentNode = remNode.parent;
	if (parentNode.data.compareTo(remNode.data) > 0)
	{
		parentNode.left = null;
		size--;
		return true;
	}
	else
	{
		parentNode.right = null;
		size--;
		return true;
	}
 }
 // Removes x from the tree.
 // Return true if x is removed; otherwise, return false;
 // Throws IllegalArgumentException if x is null.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
public boolean remove( T x)
 { 
	if (x == null)
	{
		throw new IllegalArgumentException("Error! x is null");
	}
	if (search(x) != null)
	{
		TreeNode <T> remNode = search(x);
		if (isLeaf(remNode))
		{
			return removeLeafNode(remNode);
		}
		else if (hasOneChild(remNode))
		{
			TreeNode <T> parentNode = remNode.parent;
			if (parentNode.data.compareTo(remNode.data) > 0)
			{
				//it is the left node
				parentNode.left = remNode.left;
				size--;
				return true;
			}
			else
			{
				//it is the right node 
				parentNode.right = remNode.right;
				size--;
				return true;
			}
			
		}
		else //has two children 
		{
			TreeNode <T> parentNode = remNode.parent;
			if (parentNode.data.compareTo(remNode.data) > 0)
			{
				//it is the left node
				//replace with max
				//delete max
				TreeNode <T> replace = findMaxValue(remNode);
				removeLeafNode(replace);
				remNode.data = replace.data;
				size--;
				return true;
			}
			else
			{
				//it is the right node
				TreeNode <T> replace = findMinValue(remNode);
				//TreeNode <T> replace = findMaxValue(remNode);
				removeLeafNode(replace);
				remNode.data = replace.data;
				size--;
				return true;
			}
		}
	}
	return false;
	
 }
 
 // Returns an element in the list that equals x, or null if there is no 
 // such element.
 // Throws IllegalArgumentException if x is null.
 // May call method search.
 // This is a non-recursive method.
 public T getMatch(T x)
 {
	  if (x == null)
	  {
		  throw new IllegalArgumentException("Error! x is null");
	  }
	  if (search(x) == null)
	  {
		  return null;
	  }
	  return search(x).data;
 }
 
 // Returns true if there is an element in the list that equals x.
 // Throws IllegalArgumentException if x is null.
 // May call method search.
 // This is a non-recursive method.
 public boolean contains(T x)
 {
	  if (x == null)
	  {
		  throw new IllegalArgumentException("Error! x is null");
	  }
	  return (getMatch(x) != null); 
 }
 
 // Return a reference to the node that contains an element that equals 
 // x or null if there is no such node.
 // Any method that calls this method should ensure that x is not null.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
 protected TreeNode<T> search(T x)
 {
	 if (root == null)
	 {
		 return null;
	 }
	  TreeNode <T> current = root;
	  //while current.data != x 
	  while (current.data.compareTo(x) != 0)
	  {
		  //if greater then go left 
		   if (current.data.compareTo(x) > 0)
		   {
			   current = current.left;
		   }
		   else //go right
		   {
			   current = current.right;
		   }
		   //if the data is not found 
		   if (current == null)
		   {
			   return null;
		   }
	  }
	  return current;
 }
 
 // Returns a reference to the node that contains the minimum element in 
 // the subtree rooted at node n.
 // Called by method next(); method next() should ensure that node n is 
 // not null.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
 protected TreeNode<T> getMinNode(TreeNode<T> n)
 {
	 if (root == null || n == null)
	 {
		 return null;
	 }
	 //go to the far left in the tree 
	  while (n.left != null)
	  {
		  n = n.left;
	  }
	  return n;
 }
 
 // Returns a reference to the node that contains the minimum element in 
 // the subtree rooted at node n; the found node is also removed from the 
 // tree. Note the n may be the node containing the minimum element. 
 // Any method that calls this method should ensure that n is not null
 //   and that n is not the root.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
 protected TreeNode<T> getAndRemoveMinNode(TreeNode<T> n)
 {
	 if (root == null)
	 {
		 return null;
	 }
	 if (n == root)
	 {
		 root = null;
		 return n;
	 }
	 //get min node 
	  TreeNode<T> min = getMinNode(n);
	  //get parent of the node
	  TreeNode<T> parentOfMin = min.parent;
	  //if the right of min is null
	  if (min.right == null)
	  {
		  //set the left of parent to null
		  parentOfMin.left = null;
	  }
	  else
	  {
		  //get the right node 
		  TreeNode<T> rightNode = min.right;
		  //and make it a child of parent 
		  rightNode.parent = parentOfMin;
		  parentOfMin.left = min.right;
	  }
	  return min;
 }
   
 // Returns the node that is the node after node n in sorted order,
 // as determined by an inorder traversal of the tree. The next node is 
 // node with the smallest data element greater than n.data.
 // May be called by remove().
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
protected TreeNode<T> next(TreeNode<T> n)
 {
	 //next is either right or parent
	 if (n.right != null)
	 {
		 return n.right;
	 }
	 else if (n.parent != null)
	 {
		 return n.parent;
	 }
    return null;
 }
 
 // Returns the node that is the node before n in sorted order, as
 // determined by an inorder traversal of the tree. The previous node is 
 // node with the largest data element smaller than n.data.
 // Methods next() and previous() are symmetric.
 // This is a non-recursive method.
 @SuppressWarnings("unchecked")
protected TreeNode<T> previous(TreeNode<T> n)
 {
	 if (n.left != null)
	 {
		 if (n.left.right != null)
		 {
			 return n.left.right;
		 }
		 return n.left;
	 }
	 else if (n.parent != null)
	 {
		 return n.parent;
	 }
	 return null;
 }
 
 // Returns the number of elements in the tree
 // Target Complexity: O(1)
 public int size()
 {
	 return size;
 }
 
 // Returns true if there are no elements.
 // Target Complexity: O(1)
 public boolean isEmpty()
 {
	 if (root == null)
	 {
		 return true;
	 }
	 return (root.left == null && root.right == null && root.data == null);
 }
 
 
 // Make the tree logically empty.
 // Target Complexity: O(1)
 public void clear()
 {
	  root.left = root.right = null;
	  root = null;
	  size = 0;
 }
 
 // Convenience method that returns a List of elements in sorted order.  
 // Calls getListOfElements(PrePostIn.INORDER);
 public List<T> getSortedListOfElements()
 {
	 return getListOfElements(PrePostIn.INORDER);  
 }
 
 // Returns a List of elements in the order specified by parameter order, 
 // which is either PREORDER, POSTORDER, or INORDER.
 // Calls fillListOfElements(root,order)
 public List<T> getListOfElements(PrePostIn order)
 {
	 return fillListOfElements(root, order);
  
 }
 
 // Returns a List of elements in the order specified by parameter order, 
 // which is either PREORDER, POSTORDER, or INORDER.
 // This is a non-recursive method.
 // Target Complexity: O(n)
 @SuppressWarnings("unchecked")
protected List<T> fillListOfElements(TreeNode<T> node, PrePostIn order)
 {
	 assert(node != null);
	 //create a list
	 ArrayList<T> list = new ArrayList <T>();
	 //create a stack
	 Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
	 TreeNode <T> current = root;
	   
	  switch (order)
	  {
		   case PREORDER:
			   //Root, Left, Right 
			   list = new ArrayList <T>();
			   current = node;
			   stack = new Stack<TreeNode<T>>();
			   
			   stack.add(current);
			   while (!stack.empty())
			   {
				   //pop from the stack
				   TreeNode <T> currentNode = stack.pop();
				   //add the data to the list 
				   list.add(currentNode.data);
				   //if the right is not null then add to stack
				   if (currentNode.right != null)
				   {
					   stack.push(currentNode.right);
				   }
				   //if the left is not null then add to stack 
				   if (currentNode.left != null)
				   {
					   stack.push(currentNode.left);
				   }  
			   }
			   return list;
		  case POSTORDER:
			  //Left, Right, Root 
			   list = new ArrayList <T>();
			   current = node;
			   stack = new Stack<TreeNode<T>>();
			   while (current != null || !stack.empty())
			   {
				   //check if current is null
				   while (current != null)
				   {
					   if (current.right != null)
					   {
						   //add the right child to stack 
						   stack.add(current.right);
					   }
					   //add current to stack
					   stack.add(current);
					   //go to the left subtree
					   current = current.left;
				   }
				   //get from the stack
				   current = stack.pop();
				   if(!stack.empty() && current.right == stack.peek() && current.right != null)
				   {
					   //get from the stack
					   TreeNode <T> rightNode = stack.pop();
					   //push current
					   stack.push(current);
					   current = rightNode;					   
			   	   }
				   else
				   {
					   //add data to the list
					   list.add(current.data);
					   current = null;
				   }
		   		}
			   return list;
		   case INORDER:
			   current = node; 
			   //create a new stack
			   stack = new Stack<TreeNode<T>>();
			   list = new ArrayList <T>();

			   while (!stack.empty() || current != null)
			   {
				   //if current is empty
				   if (current == null)
				   {
					   //get node from the stack
					   TreeNode<T> newNode = stack.pop();
					   //add to list
					   list.add(newNode.data);
					   //go to the right subtree
					   current = newNode.right;
					}
				   else
				   {
					   //add to stack
					   stack.push(current);
					   //go to the left subtree
					   current = current.left;
				   }
				}
			return list;   
	  }
  return list;

  
 }
 
 // Balances the tree.
 // Calls buildTree(int start, int end, List<T> L) where L is a sorted 
 // List of the elements in the tree, and start and end are the positions 
 // at the beginning and end of L.
 public void balance()
 {
	  //convert the tree to a list inorder
	  List <T> L = fillListOfElements(root, PrePostIn.INORDER);
	  if (L.size() > 0)
	  {
		  //pass in the list, 0, L.size-1
		  root = buildTree(0, L.size()-1, L);
	  }
  
 }
 
 // Helper middle to compute the middle position of a sub-list
 protected int middle(int start, int end) 
 {
  return (start + end) / 2;
 }
 
 }