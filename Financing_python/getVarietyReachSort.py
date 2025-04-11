import time
import pandas as pd
import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

# 配置无痕浏览模式和其他选项
chrome_options = Options()
# chrome_options.add_argument('--incognito')  # 启用无痕浏览
# chrome_options.add_argument('--disable-extensions')  # 禁用扩展
# chrome_options.add_argument('--disable-gpu')  # 禁用GPU加速
chrome_options.add_argument('--headless')  # 启用无头模式（可选）

# 初始化Selenium WebDriver
driver = webdriver.Chrome(options=chrome_options)

# 打开网页
driver.get('https://www.ymt.com/hangqing')
time.sleep(2)
list1 = []
list2 = []
list3 = []
count1 = 0
count2 = 0
count3 = 0

# 数据库连接配置
db_config = {
    'host': '43.139.211.116',
    'port': 3306,
    'user': 'financing_link',
    'password': 'BEjB6MSkTWZN6LPc',
    'db': 'financing_link',
    'charset': 'utf8mb4',
    'cursorclass': pymysql.cursors.DictCursor
}

# 循环获取省份项
try:
    province_items = driver.find_elements(By.CLASS_NAME, "cate_item")
    countprovince_items = 0

    for item in province_items:
        province_items = driver.find_elements(By.CLASS_NAME, "cate_item")
        count1 += 1
        list1.append({
            "id": count1,
            "parent_id": 0,
            "label": province_items[countprovince_items].text
        })
        province_items[countprovince_items].click()
        countprovince_items += 1
        product_links = driver.find_elements(By.CLASS_NAME, "product_link")
        countproduct_link = 0
        for pro in product_links:
            product_links = driver.find_elements(By.CLASS_NAME, "product_link")
            count2 += 1
            list2.append({
                "id": count2 + 100000,
                "parent_id": count1,
                "label": product_links[countproduct_link].text
            })
            product_links[countproduct_link].click()
            countproduct_link += 1
            breed_items = driver.find_elements(By.CLASS_NAME, "breed_item")
            countbreed_items = 0
            for breed in breed_items:
                count3 += 1
                list3.append({
                    "id": count3 + 100000000000,
                    "parent_id": count2 + 100000,
                    "label": breed.text
                })
                countbreed_items += 1
            # # 返回页面
            driver.find_element(By.CLASS_NAME, "menu_active").click()
            province_items = driver.find_elements(By.CLASS_NAME, "cate_item")
            province_items[countprovince_items-1].click()
        # 处理每个省份项，您可以根据需要提取文本或属性

except Exception as e:
    print(f"发生错误: {e}")

# 过滤掉 label 为 "全部" 的元素
list3 = [item for item in list3 if item["label"] != "全部"]
# 如果list2存在相同的label值，将相同值label的保留其中一个
# 保留一个后其他相同的值 list2[].id=list3[].parent_id 的list3数据 删除
# 并且删除完list3数据后，把保留一个后的其他相同的值的 list2数据也删除


# 使用字典记录每个label第一次出现的索引
unique_labels = {}
for index, item in enumerate(list2):
    label = item['label']
    if label not in unique_labels:
        unique_labels[label] = index

# 删除list2中重复的label项
new_list2 = [list2[index] for index in unique_labels.values()]

# 记录需要保留的id
keep_ids = {item['id'] for item in new_list2}

# 更新list3，只保留parent_id在keep_ids中的项
new_list3 = [item for item in list3 if item['parent_id'] in keep_ids]

# 更新list2为过滤后的新list2
list2 = new_list2
# 更新list3为过滤后的新list3
list3 = new_list3


# 插入数据到数据库
try:
    connection = pymysql.connect(**db_config)
    with connection.cursor() as cursor:

        # 清空表数据
        cursor.execute("DELETE FROM fl_varietys")

        # 创建插入SQL语句
        sql = "INSERT INTO fl_varietys (id, parent_id, label) VALUES (%s, %s, %s)"

        # 插入list1数据
        cursor.executemany(sql, [(d['id'], d['parent_id'], d['label']) for d in list1])
        # 插入list2数据
        cursor.executemany(sql, [(d['id'], d['parent_id'], d['label']) for d in list2])
        # 插入list3数据
        cursor.executemany(sql, [(d['id'], d['parent_id'], d['label']) for d in list3])

        # 提交事务
        connection.commit()
except Exception as e:
    print(f"数据库操作发生错误: {e}")
finally:
    if connection:
        connection.close()

# 最后，关闭浏览器
driver.quit()
