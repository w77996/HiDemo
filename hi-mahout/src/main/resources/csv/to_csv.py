import pymysql
import csv
import sys
import os


class ToCsv:

    def __init__(self):
        self.db = pymysql.connect(host='119.23.61.10', user='root', passwd='123456', port=8014, db='hi')
        self.cursor = self.db.cursor()
        self.last_id = 0
        self.count = 0
        self.book_count = 0
        self.album_count = 0
        self.read_count = 0
        # book_cvs_file = open('book_cvs_file.cvs', 'w')
        # book_writers = csv.writer(book_cvs_file)
        # book_writers.writerow(['user_id', 'book_id'])
        # album_cvs_file = open('album_cvs_file.cvs', 'w')
        # album_writers = csv.writer(album_cvs_file)
        # album_writers.writerow(['user_id', 'book_id'])

    def _release_db(self):
        self.db.close()
        self.cursor.close()

    def _do(self):
        while 1:
            ret = self._search_data()
            if ret == 0:
                break

    def _search_data(self):
        self.cursor.size = 50
        sql = 'select * from t_recent_listen_00 where id>%s limit 50'
        self.cursor.execute(sql, self.last_id)
        lines = self.cursor.fetchall()
        if (len(lines) > 0):
            self.last_id = lines[len(lines)-1][0]
            self.count = len(lines) + self.count;
            self._write_cvs(lines)
        if (self.count % 1000 == 0):
            print(self.count)
        return len(lines)
        # print(self.last_id)

    def _write_cvs(self, lines):
        for item in lines:
            if (item[10] == 4):
                book_cvs_file = open('book_csv_file.csv', 'a', newline='')
                book_writers = csv.writer(book_cvs_file)
                # book_writers.writerow(['user_id', 'book_id'])
                user_id = item[1]
                book_id = item[2]
                self.book_count += 1
                if (self.book_count % 1000 == 0):
                    print("book count", self.book_count)

                book_writers.writerow([str(user_id), str(book_id)])
            if (item[10] == 2):
                album_cvs_file = open('album_csv_file.csv', 'a', newline='')
                album_writers = csv.writer(album_cvs_file)
                # album_writers.writerow(['user_id', 'book_id'])
                user_id = item[1]
                book_id = item[2]
                self.album_count += 1
                if (self.album_count % 1000 == 0):
                    print("album count", self.album_count)
                album_writers.writerow([str(user_id), str(book_id)])
            if (item[10] == 10):
                read_cvs_file = open('read_csv_file.csv', 'a', newline='')
                read_writers = csv.writer(read_cvs_file)
                # album_writers.writerow(['user_id', 'book_id'])
                user_id = item[1]
                book_id = item[2]
                self.read_count += 1
                if (self.read_count % 1000 == 0):
                    print("read count", self.read_count)
                read_writers.writerow([str(user_id), str(book_id)])

    def main(self):
        pass


if __name__ == '__main__':
    c = ToCsv()
    c._do()
    c._release_db()
