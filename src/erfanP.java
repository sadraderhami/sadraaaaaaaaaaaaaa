import java.util.*;

class treemaker {
    String name;
    String value;
    ArrayList<String> namesOfTheChildren;
    HashMap<String, treemaker> nameToChild;

    public int size() {
        int count = 1;
        if (!namesOfTheChildren.isEmpty()) {
            for (String key : namesOfTheChildren) {
                count += nameToChild.get(key).size();
            }
        } else {
            return 1;
        }
        return count;
    }

    public treemaker(String name, String value) {
        this.name = name;
        this.value = value;
        this.namesOfTheChildren = new ArrayList<>();
        this.nameToChild = new HashMap<>();
    }

    public void addingchild(String key, treemaker val) {
        this.nameToChild.put(key, val);
        Collections.sort(this.namesOfTheChildren);
    }
}

public class erfanP {
    public static void main(String[] args) {
        Scanner rev = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (rev.hasNextLine()) {
            String linesinput = rev.nextLine();
            if (linesinput.isEmpty()) {
                break;
            }
            lines.add(linesinput.trim());
        }
        ArrayList<String> res = resultgiver(lines);
        ArrayList<String> initial = initialvaluegiver(lines);
        treemaker result = tresgeneral(res);
        treemaker initiall = treeinitial(initial);
        System.out.println(shomaresh(result, initiall));
    }

    public static ArrayList<String> resultgiver(ArrayList<String> a) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            String aa = a.get(i);
            if (aa.trim().equals("---")) {
                break;
            } else {
                result.add(aa);
            }

        }
        return result;
    }

    public static ArrayList<String> initialvaluegiver(ArrayList<String> a) {
        ArrayList<String> initial = new ArrayList<>();
        for (int i = a.size() - 1; i >= 0; i--) {
            String aa = a.get(i);
            if (aa.trim().equals("---")) {
                break;
            } else {
                initial.add(aa);
            }
        }
        Collections.reverse(initial);
        return initial;
    }

    public static treemaker tresJson(String keyy, ArrayList<String> res) {
        String val = "";
        String jsonn = res.get(0).trim();
        if (jsonn.charAt(0) == '{') {
            val = "{}";
        } else if (jsonn.charAt(0) == '[') {
            val = "[]";
        } else {
            val = jsonn.trim();
        }
        treemaker jj = new treemaker(keyy, val);
        if (val.equals("{}")) {
            ArrayList<String> keysandvalues = json.allkeysandqqvaluesdepartion(jsonn);
            for (String kaybavalue : keysandvalues) {
                String key = json.keyfinder(kaybavalue);
                String value = json.valuefinder(kaybavalue);
                ArrayList<String> k = new ArrayList<>();
                k.add(value);
                if (!jj.namesOfTheChildren.contains(key)) {
                    jj.namesOfTheChildren.add(key);
                }
                jj.addingchild(key, tresJson(key, k));
            }
        } else if (val.equals("[]")) {
            ArrayList<String> keysandvalues = json.allkeysandqqvaluesdepartion(jsonn);
            int barde = 0;
            for (String keybavalue : keysandvalues) {
                String key = String.valueOf(barde);
                String value = keybavalue;
                jj.namesOfTheChildren.add(key);
                ArrayList<String> k = new ArrayList<>();
                k.add(value);
                jj.addingchild(key, tresJson(key, k));
                barde++;
            }
        } else {
            return jj;
        }
        return jj;
    }

    public static treemaker treexml(String keyy, ArrayList<String> res) {
        ArrayList<String> ress = new ArrayList<>(res);
        String val = "";
        ArrayList<String> tempp = new ArrayList<>();
        if (res.size() > 1) {
            tempp = xml.valuefinder(res);
        } else {
            if (res.get(0).charAt(0) == '<') {
                tempp = xml.valuefinder(res);
            } else {
                tempp = res;
            }
        }
        if (tempp.size() > 1) {
            val = "<";
        } else {
            if (tempp.get(0).trim().charAt(0) == '<') {
                val = "<";
            } else {
                val = tempp.get(0).trim();
            }
        }
        treemaker jj = new treemaker(keyy, val);
        if (val.equals("<") || val.equals("[]")) {
            ArrayList<ArrayList<String>> keysandvalues = xml.allkeysndvaluesdepartion(ress);
            for (ArrayList<String> deraye : keysandvalues) {
                String key = xml.keyfinder(deraye);
                int barde = 0;
                ArrayList<String> value = xml.valuefinder(deraye);
                if (jj.namesOfTheChildren.contains(key)) {
                    jj.nameToChild.get(key).name = String.valueOf(barde);
                    treemaker temp = jj.nameToChild.get(key);
                    treemaker tes = new treemaker(key, "[]");
                    jj.nameToChild.remove(key);
                    String v = String.valueOf(barde);
                    jj.addingchild(key, tes);
                    tes.addingchild(String.valueOf(barde), temp);
                    tes.namesOfTheChildren.add(v);
                    barde++;
                    v = String.valueOf(barde);
                    tes.addingchild(v, treexml(v, value));
                    tes.namesOfTheChildren.add(v);
                } else {
                    jj.namesOfTheChildren.add(key);
                    jj.addingchild(key, treexml(key, value));
                }
            }
        } else {
            return jj;
        }
        return jj;
    }

    public static treemaker tresgeneral(ArrayList<String> res) {
        if (res.get(0).trim().charAt(0) == '<') {
            return treexml("root", res);
        } else {
            ArrayList<String> ree = new ArrayList<>();
            ree.add(res.get(0).trim());
            return tresJson("root", ree);
        }
    }

    public static treemaker treeinitial(ArrayList<String> res) {
        if (res.get(0).trim().charAt(0) == '<') {
            return treexml("root", res);
        } else {
            ArrayList<String> ree = new ArrayList<>();
            ree.add(res.get(0).trim());
            return tresJson("root", ree);
        }
    }

    public static int shomaresh(treemaker first, treemaker latter) {
        int count = 0;
        if (!first.name.equals(latter.name)) {
            count++;
        }
        if (first.nameToChild.size() == latter.nameToChild.size()) {
            if (first.nameToChild.isEmpty()) {
                if (first.value.charAt(0) == '"' && latter.value.charAt(0) == '"') {
                    if (first.value.equals(latter.value)) {
                        return count;
                    } else {
                        return count + 1;
                    }
                } else if (first.value.charAt(0) != '"' && latter.value.charAt(0) != '"') {
                    if (first.value.equals(latter.value)) {
                        return count;
                    } else {
                        return count + 1;
                    }
                } else {
                    return count + 2;
                }
            }
        }
        for (String key : first.nameToChild.keySet()) {
            if ((first.nameToChild.get(key).value.equals("{}") || first.nameToChild.get(key).value.equals("[]") || first.nameToChild.get(key).value.equals("<")) && latter.nameToChild.get(key).value != first.nameToChild.get(key).value) {
                count++;
            }
            if (latter.nameToChild.containsKey(key)) {
                count += shomaresh(first.nameToChild.get(key), latter.nameToChild.get(key));
            } else {
                if (Objects.equals(key, first.namesOfTheChildren.get(first.namesOfTheChildren.size() - 1))) {
                    count++;
                    count += first.nameToChild.get(key).size();
                } else {
                    count++;
                    if (!latter.namesOfTheChildren.isEmpty()) {
                        String kk = latter.namesOfTheChildren.get(latter.namesOfTheChildren.size() - 1);
                        count += latter.nameToChild.get(kk).size();
                    }
                    count++;
                    count += first.nameToChild.get(key).size();
                }
            }
        }
        return count;
    }


}

class json {
    public static ArrayList<String> allkeysandqqvaluesdepartion(String json) {
        ArrayList<String> keysandvalues = new ArrayList<>();
        boolean inString = false;
        int lev1 = 0, leve2 = 0;
        int start = 1;

        for (int i = 1; i < json.length() - 1; i++) {
            char c = json.charAt(i);

            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            } else if (!inString) {
                if (c == '{') lev1++;
                if (c == '}') lev1--;
                if (c == '[') leve2++;
                if (c == ']') leve2--;
                if (c == ',' && lev1 == 0 && leve2 == 0) {
                    keysandvalues.add(json.substring(start, i).trim());
                    start = i + 1;
                }
            }
        }
        if (start < json.length() - 1) {
            keysandvalues.add(json.substring(start, json.length() - 1).trim());
        }

        return keysandvalues;
    }

    public static String keyfinder(String keyanfvalue) {
        boolean inString = false;
        for (int i = 0; i < keyanfvalue.length(); i++) {
            if (i == 0) {
                inString = true;
            }
            if (i - 1 >= 0 && keyanfvalue.charAt(i) == '"' && !inString && keyanfvalue.charAt(i - 1) != '\\') {
                inString = true;
            } else if (i - 1 >= 0 && keyanfvalue.charAt(i) == '"' && inString && keyanfvalue.charAt(i - 1) != '\\') {
                inString = false;
            }
            if (keyanfvalue.charAt(i) == ':' && !inString) {
                return keyanfvalue.trim().substring(1, i - 1);
            }
        }
        return "dadash in json nist";
    }

    public static String valuefinder(String keyanfvalue) {
        boolean inString = false;
        for (int i = 0; i < keyanfvalue.length(); i++) {
            if (keyanfvalue.charAt(i) == ':' && !inString) {
                return keyanfvalue.substring(i + 1);
            }
        }
        return "dadash in json nist";
    }
}

class xml {
    public static int findingeverykeyandvalue(String a, ArrayList<String> xmllines) {
        StringBuilder aa = new StringBuilder(a.trim());
        aa = aa.insert(1, '/');
        String aaa = aa.toString();
        int barde = -1;
        for (String deraye : xmllines) {
            barde++;
            if (deraye.trim().equals(aaa)) {
                return barde;
            }
        }
        return -1;
    }

    public static ArrayList<String> keybazi(int start, int payan, ArrayList<String> xmll, boolean[] isitpicked) {
        ArrayList<String> keysandvalues = new ArrayList<>();
        for (int i = start; i <= payan; i++) {
            keysandvalues.add(xmll.get(i));
            isitpicked[i] = true;
        }
        return keysandvalues;
    }

    public static ArrayList<ArrayList<String>> allkeysndvaluesdepartion(ArrayList<String> xmllines) {
        ArrayList<ArrayList<String>> keysandvalues = new ArrayList<>();
        boolean[] isitpicked = new boolean[xmllines.size()];
        if (xmllines.size() == 1) {
            keysandvalues.add(xmllines);
        } else {
            for (int i = 1; i < xmllines.size() - 1; i++) {
                int deraye = findingeverykeyandvalue(xmllines.get(i), xmllines);
                if (deraye != -1 && !isitpicked[i]) {
                    keysandvalues.add(keybazi(i, deraye, xmllines, isitpicked));
                } else if (deraye == -1 && !isitpicked[i]) {
                    keysandvalues.add(keybazi(i, i, xmllines, isitpicked));
                }
            }
        }
        for (ArrayList<String> deraye : keysandvalues) {
            if (deraye.size() == 2) {
                String a = deraye.get(1).trim();
                deraye.remove(1);
                String only = deraye.get(0).trim();
                deraye.remove(0);
                StringBuilder aa = new StringBuilder(only);
                aa = aa.append(a);
                only = aa.toString();
                deraye.add(only);
            }
            if (deraye.size() == 3 && deraye.get(1).trim().charAt(0) != '<') {
                String a = deraye.get(1).trim();
                String aa = deraye.get(2).trim();
                String only = deraye.get(0);
                deraye.remove(0);
                deraye.remove(0);
                deraye.remove(0);
                StringBuilder gh = new StringBuilder(only);
                gh.append(a);
                gh.append(aa);
                only = gh.toString();
                deraye.add(only);
            }
        }
        return keysandvalues;
    }

    public static int starterofvalue(String a) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '>') {
                return i + 1;
            }
        }
        return 0;
    }

    public static int finisherofvalue(String a) {
        for (int i = a.length() - 1; i >= 0; i--) {
            if (a.charAt(i) == '<') {
                return i;
            }
        }
        return 0;
    }

    public static ArrayList<String> valuefinder(ArrayList<String> deraye) {
        ArrayList<String> derayes = new ArrayList<>();
        if (deraye.size() == 1 || deraye.size() == 0) {
            String vall = deraye.get(0);
            if (starterofvalue(vall) == finisherofvalue(vall)) {
                derayes.add("null");
                return derayes;
            } else {
                String valueofderaye = vall.substring(starterofvalue(vall.trim()), finisherofvalue(vall.trim()));
                derayes.add(valueofderaye);
            }
            return derayes;
        } else {
            deraye.remove(0);
            deraye.remove(deraye.size() - 1);
            return deraye;
        }
    }

    public static String keyfinder(ArrayList<String> deraye) {
        String firststeoofkey = deraye.get(0);
        firststeoofkey.trim();

        for (int i = 0; i < firststeoofkey.length(); i++) {
            if (firststeoofkey.charAt(i) == '>') {
                firststeoofkey = firststeoofkey.substring(0, i + 1);
                break;
            }
        }
        StringBuilder ff = new StringBuilder(firststeoofkey.trim());
        ff.deleteCharAt(0);
        ff.deleteCharAt(ff.length() - 1);
        firststeoofkey = ff.toString();
        return firststeoofkey;
    }
}