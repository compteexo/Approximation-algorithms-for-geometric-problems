import Jcg.geometry.Point_3;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Marc
 */
public class WSPD {
    // the result
    List<OctreeNode[]> listOfWSPD;
    
    public WSPD(Octree T, double s) {
        
        this.listOfWSPD = WSPD_rec(T.root, T.root, s, new LinkedList<OctreeNode[]>());

    }
    // auxiliary function
    public List<OctreeNode[]> WSPD_rec(OctreeNode u, OctreeNode v, double s, List<OctreeNode[]> l) {
        // insure that the level of u is <= the level of v
        if (u.level > v.level) {
            return WSPD_rec(v, u, s, l);
        }
        
        // if there is no point or if u and v are the same leaf
        if (u.p == null || v.p == null || (u.children==null && u.children  == null && u.p.equals(v.p))) {
            return new ArrayList<>();
         // if u and v are well s separated add (u,v)
        } else if (sSeparated(u, v, s)) {
            OctreeNode[] AB = {u, v};
            l.add(AB);
            return l;
            // go down the tree
        } else {
            for (OctreeNode child_u : u.children) {
                // use of a linkedlist in parameters to avoid concatenations
                l = WSPD_rec(child_u, v, s, l);
            }
            return l;
        }
    }

    
    // returns the boolean : "u and v are well s separated"
    boolean sSeparated(OctreeNode u, OctreeNode v, double s) {
        // if u and v are the same
        if ( (u.level == v.level && u.p.equals(v.p))) {
            return false;
        }
        // the ball radius
        double r = u.a /2 * Math.sqrt(3);
        return u.p.distanceFrom(v.p).doubleValue() < r * (s + 2);
    }
}