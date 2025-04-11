export interface VarietysVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 父id
   */
  parentId: string | number;

  /**
   * 值
   */
  label: string;

    /**
     * 子对象
     */
    children: VarietysVO[];
}

export interface VarietysForm extends BaseEntity {
  /**
   * 编号
   */
  id?: string | number;

  /**
   * 父id
   */
  parentId?: string | number;

  /**
   * 值
   */
  label?: string;

}

export interface VarietysQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 父id
   */
  parentId?: string | number;

  /**
   * 值
   */
  label?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



