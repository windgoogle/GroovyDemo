package demo

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes


class dir extends SimpleFileVisitor<Path> {
    private Path destDir;
    private Path srcDir;
    private int rootIndex;


    public CopyDirectory(String src,String dest) {
        this.srcDir=Paths.get(src);
        this.destDir=Paths.get(dest);
        rootIndex=srcDir.getNameCount();
    }

    private int getDestRootIndex() {
        int srcNameCount=srcDir.getNameCount();
        return srcNameCount;
    }

    private Path getDestFilePath(Path srcDir,Path destDir) {
        if(rootIndex>=srcDir.getNameCount())
            return destDir;
        return destDir.resolve(srcDir.subpath(rootIndex,srcDir.getNameCount()));

    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("----preVisistDirectory : "+dir);
        Path dest=getDestFilePath(dir,destDir);
        if(Files.notExists(dest))
            Files.createDirectory(dest);
        else{
            if(!Files.isDirectory(dest)) {
                Files.delete(dest);
                Files.createDirectory(dest);
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.print("----copy file : "+file);
        Files.copy(file,getDestFilePath(file,destDir),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
        System.out.println("  ok !");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("----visitFileFailed : "+file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }
}
