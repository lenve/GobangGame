package org.sang;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by sang on 2017/8/28.
 */
@ServerEndpoint("/game/{nickname}")
public class GoBangGame {
    private static final Set<GoBangGame> GO_BANG_GAME_SET = new CopyOnWriteArraySet<>();
    private Session session;
    private String nickname;
    private static String lastUser;
    private static int count = 0;
    private int[][] oriData = new int[15][25];

    @OnOpen
    public void onOpen(@PathParam("nickname") String nickname, Session session) throws IOException {
        this.session = session;
        this.nickname = nickname;
        if (GO_BANG_GAME_SET.size() > 1) {
            System.out.println("房间人满");
            session.getBasicRemote().sendText("房间人已满!");
//            session.close();
        } else {
            GO_BANG_GAME_SET.add(this);
            StringBuffer sb = new StringBuffer();
            for (GoBangGame goBangGame : GO_BANG_GAME_SET) {
                sb.append(goBangGame.nickname).append(";");
            }
            for (int i = 0; i < oriData.length; i++) {
                for (int j = 0; j < oriData[i].length; j++) {
                    oriData[i][j] = -1;
                }
            }
            sendText(nickname + "童鞋进入房间;当前房间有:" + sb.toString());
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("nickname") String nickname) {
        if (nickname == lastUser) {
            return;
        }
        int i = count++ % 2;
        String[] split = message.split("-");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        oriData[y][x] = i;
        int success = isSuccess(x, y, i, oriData);
        sendText(message + "-" + i);
        if (success == -1) {
            lastUser = nickname;
        } else {
            if (i == 0) {
                sendText("alert('白棋获胜,点击确定重新开始游戏!');location.reload(true);");
                return;
            } else if (i == 1) {
                sendText("alert('黑棋获胜,点击确定重新开始游戏!');location.reload(true);");
                return;
            }
        }
    }

    private static int isSuccess(int x, int y, int f, int[][] oriData) {
        //y的范围在0-14之间，x的范围在0-24之间
        int count = 0;
        for (int i = x - 1; i > -1; i--) {
            if (oriData[y][i] != f) {
                break;
            }
            count++;
        }
        for (int i = x + 1; i < 25; i++) {
            if (oriData[y][i] != f) {
                break;
            }
            count++;
        }
        if (count > 3) {
            return f;
        }
        count = 0;
        for (int i = y + 1; i < 15; i++) {
            if (oriData[i][x] != f) {
                break;
            }
            count++;
        }
        for (int i = y - 1; i > -1; i--) {
            if (oriData[i][x] != f) {
                break;
            }
            count++;
        }
        if (count > 3) {
            return f;
        }
        count = 0;
        for (int i = x + 1, j = y + 1; i < 25; i++, j++) {
            if (j < 15) {
                if (oriData[j][i] != f) {
                    break;
                }
                count++;
            }else
            break;
        }
        for (int i = x - 1, j = y - 1; i > -1; i--, j--) {
            if (j > -1) {
                if (oriData[j][i] != f) {
                    break;
                }
                count++;
            }else
            break;
        }
        if (count > 3) {
            return f;
        }
        count = 0;
        for (int i = x + 1, j = y - 1; i < 25; i++, j--) {
            if (j > -1) {
                if (oriData[j][i] != f) {
                    break;
                }
                count++;
            }else
            break;
        }
        for (int i = x - 1, j = y + 1; i > -1; i--, j++) {
            if (j < 25) {
                if (oriData[j][i] != f) {
                    break;
                }
                count++;
            }else
            break;
        }
        if (count > 3) {
            return f;
        }
        return -1;
    }

    private static void sendText(String msg) {
        for (GoBangGame goBangGame : GO_BANG_GAME_SET) {
            try {
                synchronized (goBangGame) {
                    goBangGame.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                GO_BANG_GAME_SET.remove(goBangGame);
                try {
                    goBangGame.session.close();
                } catch (IOException e1) {
                }
                sendText(goBangGame.nickname + "童鞋已下线");
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        GO_BANG_GAME_SET.remove(this);
        for (GoBangGame goBangGame : GO_BANG_GAME_SET) {
            if (goBangGame.nickname == this.nickname) {
                sendText(this.nickname + "童鞋已下线");
            }
        }
    }
}
