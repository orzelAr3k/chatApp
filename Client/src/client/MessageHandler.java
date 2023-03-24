// package client;

// import javax.swing.DefaultListModel;
// import javax.swing.ListModel;
// import javax.swing.SwingUtilities;
// import java.util.HashMap;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;

// public class MessageHandler {

//     private javax.swing.JList<String> messagesJList;
//     private javax.swing.JList<String> userJList;

//     public MessageHandler(javax.swing.JList<String> messagesJList, javax.swing.JList<String> userJList) {
//         this.messagesJList = messagesJList;
//         this.userJList = userJList;
//     }

//     public HashMap<String, ArrayList<Message>> messagesList = new HashMap<String, ArrayList<Message>>();
//     public List<String> userList = new ArrayList<String>();

//     public void handleMessage(String jsonString) {
//         SwingUtilities.invokeLater(() -> {
//             try {
//                 JSONObject json = (JSONObject) new JSONParser().parse(jsonString);

//                 // Set values
//                 String username = (String) json.get("username");
//                 String message = (String) json.get("message");
//                 String from = (String) json.get("from");
//                 String time = (String) json.get("time");

//                 if (username != null && message != null && time != null) {
//                     messagesList.get(from).add(new Message(username, message, time));
//                 }

//                 JSONArray usersArray = (JSONArray) json.get("users");
//                 if (usersArray != null) {
//                     userList = (List<String>) usersArray.stream().map(Object::toString).collect(Collectors.toList());
//                     userJList.setModel(new javax.swing.AbstractListModel<String>() {
//                         public int getSize() {
//                             return userList.size();
//                         }

//                         public String getElementAt(int i) {
//                             return userList.get(i);
//                         }
//                     });
//                 }

//             } catch (ParseException e) {
//                 e.printStackTrace();
//             }
//         });
//     }
// }
