import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class EditDialog extends AddDialog{
    public EditDialog(Frame parent, TodoItem item) {
        super(parent, item);
        ads.setText("Confirm");
        nn.setText(item.getName());
        name=item.getName();

        desc = item.getNotes();
        ss.setText(item.getNotes());
        int y = item.getDate().getYear();
        int d = item.getDate().getDayOfMonth();
        int m = item.getDate().getMonthValue();

        //model.setDate();
        datePicker.getModel().setDate(y,m,d);
        datePanel.getModel().setDate(y,m,d);

        ti.setText(item.getTime().toString());

    }
}
