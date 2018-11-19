package Scheduler.ListConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlainTextListConverter implements ListConverter{

    private Stream<String> csvStream;

    public PlainTextListConverter(){

    }

    public PlainTextListConverter(String path) throws IOException{
        setPath(path);
    }

    private String getFileExtension(String fileName){
        if(fileName == null)
            throw new IllegalArgumentException("File name cannot be null.");
        int indexOfDot = fileName.lastIndexOf('.');
        if(indexOfDot == -1)
            return "";
        else
            return fileName.substring(indexOfDot + 1);
    }

    public void setPath(String path) throws IOException{
        String extension = getFileExtension(path);
        if(extension.equals("") || !extension.equals("csv"))
            throw new IllegalArgumentException("CSV Extension not found. Incompatible file type.");
        csvStream = Files.lines(Paths.get(path));
    }

    @Override
    public List<String> convertToList(){
        if(csvStream == null)
            throw new NullPointerException("Path to file must be set");
        return csvStream.collect(Collectors.toList());
    }

}
