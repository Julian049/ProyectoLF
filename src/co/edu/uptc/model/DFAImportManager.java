package co.edu.uptc.model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class DFAImportManager {

    private Gson gson;

    public DFAImportManager() {
        this.gson = new Gson();
    }

    public DFA importFromFile(String filePath) throws IOException, Exception {
        if (this.gson == null) {
            this.gson = new Gson();
        }

        try (FileReader reader = new FileReader(filePath)) {
            DFA importedDFA = gson.fromJson(reader, DFA.class);

            if (importedDFA == null) {
                throw new Exception("El archivo JSON está vacío o tiene formato incorrecto");
            }

            return importedDFA;
        }
    }
}
