package Util.Clases;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Adaptador extends TypeAdapter<LocalDateTime> {

    //Esta clase es literalmente un adaptador de la variable o clase LocalDate ya que al parecer el archivo en formato .json
    // no puede transformarla adecuadamente
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(localDateTime.format(formatter));
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String s = jsonReader.nextString();
        return LocalDateTime.parse(s, formatter);
    }
}

