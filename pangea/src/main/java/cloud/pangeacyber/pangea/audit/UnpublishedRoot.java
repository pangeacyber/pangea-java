package cloud.pangeacyber.pangea.audit;

public class UnpublishedRoot {
    String treeId;
    String coldTreeSize;
    String treeSize;
    String hash;

    public UnpublishedRoot(String treeId, String coldTreeSize, String treeSize, String hash) {
        this.treeId = treeId;
        this.coldTreeSize = coldTreeSize;
        this.treeSize = treeSize;
        this.hash = hash;
    }

    public static UnpublishedRoot fromString(String encodedUnpublishedRoot){
        if(encodedUnpublishedRoot == null){
            return null;
        }

        String[] parts = encodedUnpublishedRoot.split(",");
        if(parts.length != 4){
            return null;
        }
        return new UnpublishedRoot(parts[0], parts[1], parts[2], parts[3]);
    }

    public String encode(){
        return String.format("%s,%s,%s,%s", treeId, coldTreeSize, treeSize, hash);
    }

    public String getTreeId() {
        return treeId;
    }

    public String getColdTreeSize() {
        return coldTreeSize;
    }

    public String getTreeSize() {
        return treeSize;
    }

    public String getHash() {
        return hash;
    }
}
