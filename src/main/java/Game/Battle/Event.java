package Game.Battle;

public class Event {

    private final String event;
    private String[] field;

    public Event(String event, String ... field)
    {
        this.event = event;
        this.field = field;
    }

   public void applyTransformation(String markup)
   {
       for (int i = 0; i < field.length; i++)
           field[i] = markup + field[i] + markup;
   }

   public String getEvent()
   {
       return String.format(event, field);
   }
}
