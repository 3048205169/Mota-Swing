#魔塔的设计文件
##1.    类
###1.1  gameFrame
整个game的frame，必须要有
###1.2  bottom
黑板所在的地方，也是勇士活动的游戏盘的地盘
###1.3  gamePanel
gameFrame的下属
###1.4  bgLabel

###1.5  floor
存放的是整个棋盘,[21][11][11]的数组，21是楼层数量，11x11的棋盘
###1.6  hero
代表的是英雄，英雄有攻击力，防御力，生命还有坐标，当然还有它的标签
###1.7  celestial
代表的是仙子
###1.8  yellowDoors
存放的是所有的黄门
###1.9  wallCells
存放的所有的墙
###1.10 lavas
存放的所有的岩浆
###1.11 darkStars
存放所有的暗色星墙
###1.12 upstairs
存放的所有的上楼
###1.13 downstairs
存放的所有的下楼

##2.   游戏的流程
