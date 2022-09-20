# Quy cách code

Quy cách code được tự động kiểm tra bằng [Checkstyle](https://checkstyle.org) khi push lên GitHub.
Có thể tạo báo cáo lỗi quy cách bằng lệnh `mvn jxr:jxr site checkstyle:checkstyle`.

Tệp cấu hình cho Checkstyle là `Checkstyle configuration.xml`.

## Cơ bản
- Mức thụt lề là 4 dấu cách, không dùng tab.
- Mỗi dòng dài không quá 100 kí tự.
- Tệp mã nguồn kết thúc bằng một dấu xuống dòng.

## Đặt tên
- Chỉ dùng chữ cái tiếng Anh, chữ số và `_`.
- Tên package chỉ gồm dấu chấm, chữ cái thường và chữ số.
- Coi cụm từ viết tắt là một từ.
- Tên lớp viết `PascalCase`.
- Tên hằng viết `ALL_CAPS`.
- Tên biến, phương thức viết `camelCase`.

## `import`
- Không dùng `import` với `*`.
- Không dùng `import static`.
- Không để thừa `import`.

## Thiết kế lớp
- Mỗi tệp mã nguồn chỉ chứa một lớp mức trên cùng có cùng tên.
- Nếu lớp chỉ toàn thành viên `static`, hàm tạo phải để `private`.
- Không để sót hàm `main` với các lớp không phải lớp khởi động chương trình.
- Các phương thức cùng tên phải để cạnh nhau.
- Nếu có `equals(T)` mà `T` không phải `Object` thì phải có `equals(Object)`.
- Không viết `finalize`.

## Annotation
- Luôn đặt annotation phía trên đối tượng áp dụng.
- Luôn đặt `@Override` cho các phương thức cài đè.

## Khối lệnh
- Đặt dấu `{` ở cùng dòng.
- Đặt dấu `}` ở dòng riêng.
- Mọi cấu trúc điều khiển đều phải có nội dung là khối lệnh không rỗng, trừ `while` có thể là khối
  lệnh rỗng.

## Viết mã
- Viết mỗi lệnh trên một dòng riêng.
- Chỉ khai báo một biến mỗi dòng.
- Mọi biến được khai báo phải được sử dụng.
- Mọi biến cục bộ không bị thay đổi giá trị sau khi khai báo phải có `final`.
- Không gán biến trong biểu thức tính toán.
- Không gán biến tham số.
- Đặt kí hiệu kiểu mảng `[]` sau tên kiểu phần tử.
- Không để dấu phẩy thừa trong danh sách phần tử mảng hoặc `enum`.
- Không viết `super();`.
- Không viết lệnh rỗng (chỉ có `;`).

## Javadoc
- Đặt ở phía trên đối tượng và các thành phần cú pháp liên quan đến nó (annotation, modifier,...).
- Mỗi dòng ở giữa phải bắt đầu bằng dấu `*`.

## Khoảng trắng
- Không để nhiều hơn một dòng trống liên tiếp.
- Không để nhiều hơn một khoảng trắng giữa các token.
- Không để khoảng trắng ở đầu và cuối bên trong ngoặc tròn, danh sách giá trị.
- Không để khoảng trắng sau `()` của cú pháp ép kiểu.
- Không để khoảng trắng trước dấu `:` của `case` và `default`.
- Để khoảng trắng trước `{`.
- Để khoảng trắng quanh `()` và các từ khóa của các cấu trúc điều khiển, `&&`, `||`, `?` và `:` của
  toán tử điều kiện.
- Nếu biểu thức trải nhiều dòng, đầu mỗi dòng phải là toán tử.

## Khác
- Không thụt dòng chú thích.
- Thứ tự modifier: `public`, `protected`, `private`, `abstract`, `default`, `static`, `final`,
  `transient`, `volatile`, `synchronized`, `native`, `strictfp`.
- Khi đánh dấu deprecated, phải viết cả annotation `@Deprecated` và `@deprecated` trong Javadoc.