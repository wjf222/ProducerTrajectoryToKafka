package objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class TracingPoint implements Comparable<TracingPoint>{
    // 轨迹编号
    public int id;
    // 经度
    public double longitude;
    // 纬度
    public double latitude;
    // 时间
    public long date;

    @Override
    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(this.date);
        return String.format("%d,%s,%f,%f",id,simpleDateFormat.format(date),longitude,latitude);
    }
    @Override
    public int compareTo(TracingPoint o) {
        return (int) (date-o.date);
    }

    public static TracingPoint fromString(String str) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String[] s = str.split(",");
        TracingPoint tracingPoint = TracingPoint.builder()
                .id(Integer.parseInt(s[0]))
                .date(ft.parse(s[1]).getTime())
                .longitude(Double.parseDouble(s[2]))
                .latitude(Double.parseDouble(s[3])).build();
        return tracingPoint;
    }
}
