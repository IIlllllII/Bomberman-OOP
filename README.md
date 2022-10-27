
<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/dat-roy/Bomberman-OOP.git">
    <img src="res/images/ttsalpha4.0@0.5x.png" alt="Logo" width="200" height="200">
  </a>

<h3 align="center">OOP BOMBERMAN PROJECT</h3>
<h4 align="center">Nhóm 7 - Mã lớp học phần: INT2204 20</h4>

  <p align="center">
    From UET with love!
    <br />
    <br />
    <a href="">DEMO VIDEO (UPDATE SOON)</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
# Table of contents :round_pushpin:
1. [Giới thiệu](#Introduction)
2. [](#Game)
3. [Biểu đồ lớp UML](#UML-class-diagram)
4. [Features](#Features)
5. [Challenges](#Challenges)
6. [Acknowledgments](#Acknowledgments)
7. [References](#References)



# **Bài tập lớn OOP - Bomberman Game**
## BTL nhóm 7 gồm các thành viên:
- Trần Đức Vinh, MSV 21020098
- Lê Viết Đạt, MSV 21020298
- Nguyễn Ngọc Huy, MSV 21020765

## Mô tả về các đối tượng trong trò chơi
Nếu bạn đã từng chơi Bomberman, bạn sẽ cảm thấy quen thuộc với những đối tượng này. Chúng được được chia làm hai loại chính là nhóm đối tượng động (*Bomber*, *Monster*, *Bomb*) và nhóm đối tượng tĩnh (*Grass*, *Wall*, *Brick*, *Portal*, *Item*).
- ![](/src/main/resources/icon/bomber.png) *Bomber* là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- ![](/src/main/resources/icon/ballom.png) *Enemy* là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](/src/main/resources/icon/bomb.png) *Bomb* là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng *Flame* được tạo ra.
- ![](/src/main/resources/sprites/map/grass/grass2.png) *Grass* là đối tượng mà Bomber và Monster có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](/src/main/resources/sprites/map/wall/wall2.png) *Wall* là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Monster không thể di chuyển vào đối tượng này
- ![](/src/main/resources/sprites/map/brick/brick2.png) *Brick* là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Monster thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.
- ![](/src/main/resources/sprites/map/port.png) *Portal* là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Monster đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

Các *Item* cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:

- ![](/src/main/resources/icon/speedUp.png) *SpeedUp*: Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
- ![](/src/main/resources/icon/flameUp.png) *FlameUp*: Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
- ![](/src/main/resources/icon/bombUp.png) *BombUp*: Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, Bomber sẽ được đặt và kích hoạt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb có thể đặt thêm một.
- ![](/src/main/resources/icon/liveUp.png) *LivesUp*: Khi sử dụng Item này, Bomber sẽ được tăng 1 mạng.
- ![](/src/main/resources/icon/brickPass.png) *BrickPass*: Khi sử dụng Item này, Bomber sẽ có khả năng đi qua *Brick* trong 45s.
- ![](/src/main/resources/icon/flamePass.png) *FlamePass*: Khi sử dụng Item này, Bomber sẽ có khả năng đi qua *Flame* trong 45s.
- ![](/src/main/resources/icon/bombPass.png) *BombPass*: Khi sử dụng Item này, Bomber sẽ có khả năng đi qua *Bomb* trong 45s.
- ![](/src/main/resources/icon/invincible.png) *Invincible*: Khi sử dụng Item này, Bomber sẽ bất tử trong 30s.
- ![](/src/main/resources/icon/coin.png) *Coin*: Sau khi người chơi vượt qua màn, tất cả Brick chưa bị phá hủy sẽ hóa thành Coin trong 15s, nhiệm vụ của người chơi là di chuyển ăn nhiều Coin nhất có thể. Ăn 1 coin người chơi sẽ được cộng 100 điểm.

Có nhiều loại Enemy trong Bomberman:

- ![](/src/main/resources/icon/ballom.png) *Ballom*: Balloom là Enemy đơn giản nhất, di chuyển ngẫu nhiên với vận tốc cố định. Khi giết Ballom người chơi sẽ được cộng 100 điểm.
- ![](/src/main/resources/icon/oneal.png) *Oneal*: Có tốc độ di chuyển thay đổi, lúc nhanh, lúc chậm và di chuyển "thông minh" hơn so với Balloom. Nó sẽ di chuyển về phía Bomber khi anh ta ở gần. Khi giết Oneal người chơi sẽ được cộng 200 điểm.
- ![](/src/main/resources/icon/dahl.png) *Dahl*: Tốc độ hơi nhanh, có động tác nảy, không thông minh, thậm chí kém thông minh hơn Ballom không cố gắng đuổi theo Bomber, thích di chuyển từ trái sang phải, đôi ki di chuyển lên xuống. Khi giết Dahl người chơi sẽ được cộng 400 điểm.
- ![](/src/main/resources/icon/minvo.png) *Minvo*: Di chuyển như Onils, bắt gặp sau Dahl. Thường sẽ truy đuổi Bomber nếu ở gần. Khi giết Minvo người chơi sẽ được cộng 800 điểm.
- ![](/src/main/resources/icon/doria.png) *Doria*: Nó có thể di chuyển qua các Brick, di chuyển chậm, thông minh, nó có thể đuổi theo Bomber.  Khi giết Dahl người chơi sẽ được cộng 1000 điểm.
- ![](/src/main/resources/icon/ovape.png) *Ovape*: Đi qua Brick, không đuổi Bomberman, có khả năng vượt Wall. Khi giết Minvo người chơi sẽ được cộng 2000 điểm.
- ![](/src/main/resources/icon/pass.png) *Pass*: Di chuyển nhanh hơn hầu hết kẻ thù, ngoại trừ Pontan, nhanh hơn và nguy hiểm hơn, tránh bom, đuổi Bomber. Khi giết Minvo người chơi sẽ được cộng 4000 điểm.
- ![](/src/main/resources/icon/pontan.png) *Pontan*: Di chuyển rất nhanh, đi qua các Brick, liên tục truy đuổi bomber. Khi giết Minvo người chơi sẽ được cộng 8000 điểm.
- *Banana*
- *Oyabee*
- *Komori*
- *Saru*

## Mô tả game play, xử lý va chạm và xử lý bom nổ

- Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy và tìm ra vị trí Portal để có thể qua màn mới
- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ. Lúc đấy Bomber bị trừ 1 mạng, khi Bomber hết mạng trò chơi kết thúc.
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.

- Khi Bomb nổ, một Flame trung tâm tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên/dưới/trái/phải. Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các Item FlameUp.
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Brick/Wall nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Brick/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Brick/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng. Còn nếu vật cản Flame là một đối tượng Bomb khác thì đối tượng Bomb đó cũng sẽ nổ ngay lập tức.
- Sau khi giết hết Enemy và tìm được Portal, tất cả các Brick còn lại sẽ hóa thành Coin, người chơi có nhiêm vụ ăn nhiều Coin nhất có thể trong 15s, hết 15s người chơi được qua màn tiếp theo.

## Các chức năng trong game

- Lựa chọn chế độ chơi (Auto Play, classic, training)
- Tùy chỉnh âm thanh
- Zoom màn hình
- Hướng dẫn chơi
- Hiện thị bảng xếp hạng



