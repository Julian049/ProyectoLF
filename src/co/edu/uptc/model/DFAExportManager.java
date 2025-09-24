package co.edu.uptc.model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class DFAExportManager {
    private Gson gson;

    public DFAExportManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void exportToFile(DFA dfa, String filepath)  throws IOException {
        try(FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(dfa, writer);
        }
    }
}
