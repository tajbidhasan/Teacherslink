module com.example.teacherslink {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.teacherslink to javafx.fxml;
    exports com.example.teacherslink;


}