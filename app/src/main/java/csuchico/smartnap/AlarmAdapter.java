package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jguil on 10/18/2017.
 */


public class AlarmAdapter extends ArrayAdapter<AlarmClock> {
  public AlarmAdapter(Activity context, ArrayList<AlarmClock> alarms) {
    super(context, 0, alarms);
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View listItemView = convertView;
    if(listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(
              R.layout.l_item, parent, false);
     }

    final AlarmClock currentAlarm = getItem(position);

    TextView alarmTextView = (TextView) listItemView.findViewById(R.id.alarm_text_view);
    Button alarmEdit = (Button) listItemView.findViewById(R.id.toggle_view);

    String formattedAlarmTime = currentAlarm.getTimeFormatted("h:mm a");

    alarmTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String key = Integer.toString(R.string.extraKey_alarm);
        Intent intent = new Intent(getContext(),AlarmEdit.class);
        Bundle data = new Bundle();
        long alarmId = currentAlarm.getId();
        data.putLong(key,alarmId);
        intent.putExtras(data);
        view.getContext().startActivity(intent);
      }
    });

    alarmTextView.setText(formattedAlarmTime);

    return listItemView;
  }
}

