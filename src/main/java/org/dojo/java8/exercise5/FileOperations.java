package org.dojo.java8.exercise5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.dojo.java8.model.Address;
import org.dojo.java8.model.Role;
import org.dojo.java8.model.User;


public class FileOperations {

    //TODO Replace By Files.lines, use static method reference
    public static List<User> loadUsersFromCsv(Path csvPath) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath.toFile()))) {
//            String line;
//            boolean firstLine = true;
//            List<User> users = new ArrayList<>();
//            while ((line = reader.readLine()) != null) {
//                if (!firstLine) {
//                    users.add(lineToUser(line));
//                }
//                firstLine = false;
//            }
//
//            return users;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
        
        try  {
            return Files.lines(csvPath)
                    .skip(1)
                    .map(FileOperations::lineToUser)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //TODO Replace by Files.walk and remove visitor. Use Optional.orElseThrow for throw FileNotFoundException
    public static Path findRecursivelyFileByName(String path, String fileName) throws IOException {
//        Path rootDirectory = Paths.get(path);
//
//        SearchVisitor searchVisitor = new SearchVisitor(fileName);
//
//        Files.walkFileTree(rootDirectory, searchVisitor);
//        Path fileFound = searchVisitor.fileFound;
//        if (fileFound == null) {
//            throw new FileNotFoundException();
//        }
//        return fileFound;
        
        return Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS)
                .filter(currentPath -> currentPath.getFileName().equals(Paths.get(fileName)))
                .findFirst()
                .orElseThrow(FileNotFoundException::new);
    }

    public static class SearchVisitor extends SimpleFileVisitor<Path> {

        private Path fileFound;

        private String fileNameToSearch;

        public SearchVisitor(String fileNameToSearch) {
            this.fileNameToSearch = fileNameToSearch;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile() && file.getFileName().toString().equals(fileNameToSearch)) {
                fileFound = file;
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }

    }

    private static User lineToUser(String line) {
        String[] columns = line.split(",");
        User user = new User(columns[0], columns[1], columns[2]);
        user.withLogin(columns[3])
                .withPassword(columns[4])
                .withExpireDate(LocalDate.parse(columns[5], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.")))
                .withRole(Role.valueOf(columns[6]))
        ;
        if (columns.length > 8) {
            user.withAddress(new Address(columns[7], columns[8], columns[9]));
        }

        return user;
    }

}

