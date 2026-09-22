import java.util.ArrayList;

public class Relation {
    ArrayList<String> header;
    ArrayList<Row> rows;

    public Relation() {
        header = new ArrayList<>();
        rows = new ArrayList<>();
    }

    Relation(String csvfile) {
        CSVLoader loader = new CSVLoader(csvfile);
        Relation loaded = loader.loadCSVinRelation();
        header = loaded.header;
        rows = loaded.rows;
    }

    public Relation Selection(String key, String value) {
        Relation result = new Relation();
        result.header = new ArrayList<>(this.header);
        int posizione = -1;

        for (int i = 0; i < this.header.size(); i++) {
            if (this.header.get(i).equals(key)) {
                posizione = i;
            }
        }

        if (posizione == -1) {
            return result;
        }
        for (int i = 0; i < this.rows.size(); i++) {
            Row row = this.rows.get(i);
            if (row.values.get(posizione).equals(value)) {
                result.rows.add(row);
            }
        }
        return result;
    }

    public Relation Projection(ArrayList<String> keys) {
        Relation result = new Relation();

        // trovo le colonne richieste
        for (int i = 0; i < keys.size(); i++) {
            for (int j = 0; j < this.header.size(); j++) {
                if (this.header.get(j).equals(keys.get(i))) {
                    result.header.add(this.header.get(j));
                }
            }
        }

        // creo le nuove righe
        for (int i = 0; i < this.rows.size(); i++) {
            Row nuovaRiga = new Row();
            for (int j = 0; j < keys.size(); j++) {
                for (int k = 0; k < this.header.size(); k++) {
                    if (this.header.get(k).equals(keys.get(j))) {
                        nuovaRiga.values.add(this.rows.get(i).values.get(k));
                    }
                }
            }
            result.rows.add(nuovaRiga);
        }
        return result;
    }

    public Relation Union(Relation one, Relation two) {
        Relation result = new Relation();

        // controllo se gli header sono congrui
        if (this.header.size() != two.header.size()) {
            return result;
        }
        for (int i = 0; i < this.header; i++) {
            
        }
        
    }
}
