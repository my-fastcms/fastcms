package com.fastcms.web;

import com.fastcms.common.model.TreeNode;
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
        List<FileTreeNode> treeNodeList = Files.walk(rootPath).filter(item -> !item.getParent().toString().contains("fonts"))
                .map(item -> new FileTreeNode(item.getParent().toString(), item.getFileName().toString())).collect(Collectors.toList());

        System.out.println(treeNodeList.size());
        System.out.println(rootPath.toString());

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
            fileTreeNodeMap.put(dir.toString(), new FileTreeNode(dir.toString(), dir.getFileName().toString()));
            FileTreeNode fileTreeNode = fileTreeNodeMap.get(dir.getParent().toString());
            if (fileTreeNode != null) {
                if(fileTreeNode.getChildren() == null) {
                    fileTreeNode.setChildren(new ArrayList<>());
                }
                fileTreeNode.getChildren().add(new FileTreeNode(dir.getParent().toString(), dir.getFileName().toString()));
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
                fileTreeNode.getChildren().add(new FileTreeNode(file.getParent().toString(), file.getFileName().toString()));
            }
        }

    }

    class FileTreeNode extends TreeNode {

        private String parent;

        public FileTreeNode(String parent, String label) {
            super(label);
            this.parent = parent;
        }

    }

}
