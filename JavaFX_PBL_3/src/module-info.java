module JavaFX_PBL_3 {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens applicationcontroller to javafx.fxml;
	opens applicationmain to javafx.graphics;
}
