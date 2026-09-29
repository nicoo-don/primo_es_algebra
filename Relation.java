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

        result.header = new ArrayList<>(this.header); // copio gli header della relazione originale
        int posizione = -1; // posizione della colonna che cerchiamo

        // cerco nell'header la posizione della chiave
        for (int i = 0; i < this.header.size(); i++) {
            if (this.header.get(i).equals(key)) {
                posizione = i;
            }
        }
        // se la chiave non esiste, restituisco una relazione vuota
        if (posizione == -1) {
            return result;
        }
        // controllo tutte le righe della relazione
        for (int i = 0; i < this.rows.size(); i++) {
            Row row = this.rows.get(i);
            // controllo se il valore della colonna è uguale a quello cercato
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
                // se trovo la colonna richiesta, la aggiungo agli header
                if (this.header.get(j).equals(keys.get(i))) {
                    result.header.add(this.header.get(j));
                }
            }
        }

        // creo le nuove righe
        for (int i = 0; i < this.rows.size(); i++) {
            Row nuovaRiga = new Row();
            // cerco nelle righe solo le colonne richieste
            for (int j = 0; j < keys.size(); j++) {
                for (int k = 0; k < this.header.size(); k++) {
                    // se trovo la colonna richiesta aggiungo il suo valore alla nuova riga
                    if (this.header.get(k).equals(keys.get(j))) {
                        nuovaRiga.values.add(this.rows.get(i).values.get(k));
                    }
                }
            }
            result.rows.add(nuovaRiga);
        }
        return result;
    }

    public Relation Union(Relation two) {
        Relation result = new Relation();

        // controllo se gli header sono congrui
        if (this.header.size() != two.header.size()) {
            return result;
        }
        for (int i = 0; i < this.header.size(); i++) {
            if (!this.header.get(i).equals(two.header.get(i))) {
                return result;
            }
        }
        // copio gli header
        result.header = new ArrayList<>(this.header);

        // aggiungo le righe della prima relazione
        for (int i = 0; i < this.rows.size(); i++) {
            result.rows.add(this.rows.get(i));
        }
        // aggiungo le righe della seconda relazione solo se non sono già presenti
        for (int i = 0; i < two.rows.size(); i++) {
            Row row = two.rows.get(i);
            boolean presente = false;

            for (int j = 0; j < result.rows.size(); j++) {
                if (result.rows.get(j).values.equals(row.values)) {
                    presente = true;
                }
            }
            if (!presente) {
                result.rows.add(row);
            }
        }
        return result;
    }


    public Relation Difference(Relation two) {
        Relation result = new Relation();

        // controllo se gli header sono congrui
        if (this.header.size() != two.header.size()) {
            return result;
        }
        for (int i = 0; i < this.header.size(); i++) {
            if (!this.header.get(i).equals(two.header.get(i))) {
                return result;
            }
        }
        // copio gli header
        result.header = new ArrayList<>(this.header);

        // cerco le righe della prima relazione che NON sono presenti nella seconda
        for (int i = 0; i < this.rows.size(); i++) {
            Row row = this.rows.get(i);
            boolean presente = false;

            for (int j = 0; j < two.rows.size(); j++) {
                if (row.values.equals(two.rows.get(j).values)) {
                    presente = true;
                }
            }
            if (!presente) {
                result.rows.add(row);
            }
        }
        return result;
    }
}
