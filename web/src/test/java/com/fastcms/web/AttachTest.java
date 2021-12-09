package com.fastcms.web;

import com.fastcms.common.model.TreeNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.io.file.CountingPathVisitor;
import org.apache.commons.io.file.SimplePathVisitor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

/**
 * wjun_java@163.com
 */
@SpringBootTest
public class AttachTest {

    Path rootPath = Paths.get("F:\\wangjun\\ideaProjects\\fastcms\\web\\src\\main\\resources\\htmls\\fastcms");

    @Test
    public void test() {
        try {
            Path path = Files.walkFileTree(rootPath, CountingPathVisitor.withBigIntegerCounters());
            System.out.println(path.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWalk() throws IOException {
        List<FileTreeNode> treeNodeList = Files.walk(rootPath).filter(item -> !item.toString().endsWith(".properties"))
                .map(item -> new FileTreeNode(item))
                .sorted(Comparator.comparing(FileTreeNode::getSortNum)).collect(Collectors.toList());

        getChildren(treeNodeList.get(0), treeNodeList);

        System.out.println(treeNodeList.size());
        System.out.println(rootPath.toString());

    }

    void getChildren(FileTreeNode fileTreeNode, List<FileTreeNode> fileTreeNodeList) {
        List<TreeNode> childrenNodeList = fileTreeNodeList.stream().filter(item -> Objects.equals(item.getParent(), fileTreeNode.getPath())).collect(Collectors.toList());
        if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
            fileTreeNode.setChildren(childrenNodeList);
            childrenNodeList.forEach(item -> getChildren((FileTreeNode) item, fileTreeNodeList));
        }
    }

    @Test
    public void testWalkTree() throws IOException {
        FileTreeNodePathVisitor fileTreeNodePathVisitor = new FileTreeNodePathVisitor();
        Path path = Files.walkFileTree(rootPath, fileTreeNodePathVisitor);
        System.out.println(fileTreeNodePathVisitor);
        System.out.println(path);
    }

    @Test
    public void testList() throws IOException {
        Files.list(rootPath).forEach(item -> System.out.println(item));
    }

    class FileTreeNodePathVisitor extends SimplePathVisitor {

        final Map<String, FileTreeNode> fileTreeNodeMap = Collections.synchronizedMap(new HashMap<>());

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("====preVisitDirectory:" + dir);
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            System.out.println("====postVisitDirectory:" + dir);
            fileTreeNodeMap.put(dir.toString(), new FileTreeNode(dir));
            FileTreeNode fileTreeNode = fileTreeNodeMap.get(dir.getParent().toString());
            if (fileTreeNode != null) {
                if(fileTreeNode.getChildren() == null) {
                    fileTreeNode.setChildren(new ArrayList<>());
                }
                fileTreeNode.getChildren().add(new FileTreeNode(dir));
            }
            return super.postVisitDirectory(dir, exc);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("====visitFile:" + file);
            addFile(file);
            return super.visitFile(file, attrs);
        }

        private void addFile(Path file) {
            FileTreeNode fileTreeNode = fileTreeNodeMap.get(file.getParent().toString());
            if (fileTreeNode != null) {
                if(fileTreeNode.getChildren() == null) {
                    fileTreeNode.setChildren(new ArrayList<>());
                }
                fileTreeNode.getChildren().add(new FileTreeNode(file));
            }
        }

    }

    class FileTreeNode extends TreeNode {

        @JsonIgnore
        private String parent;

        @JsonIgnore
        private String path;

        public FileTreeNode(Path path) {
            super(path.getFileName().toString(), Files.isDirectory(path) ? 0 : 1);
            this.parent = path.getParent().toString();
            this.path = path.toString();
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

}
