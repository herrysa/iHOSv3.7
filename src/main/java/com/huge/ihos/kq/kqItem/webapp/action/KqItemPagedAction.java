package com.huge.ihos.kq.kqItem.webapp.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.model.KqItemTreeNode;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

public class KqItemPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2914521789329924228L;
	private KqItemManager kqItemManager;
	private List<KqItem> kqItems;
	private KqItem kqItem;
	private String kqItemId;
	private List<KqItemTreeNode> kqItemTreeNodes;
	private String parentId;
	private KqItemTreeNode itemNode;
	private List<ZTreeSimpleNode> nodes;

	public List<ZTreeSimpleNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ZTreeSimpleNode> nodes) {
		this.nodes = nodes;
	}

	public KqItemTreeNode getItemNode() {
		return itemNode;
	}

	public void setItemNode(KqItemTreeNode itemNode) {
		this.itemNode = itemNode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<KqItemTreeNode> getKqItemTreeNodes() {
		return kqItemTreeNodes;
	}

	public void setKqItemTreeNodes(List<KqItemTreeNode> kqItemTreeNodes) {
		this.kqItemTreeNodes = kqItemTreeNodes;
	}

	public List<KqItem> getKqItems() {
		return kqItems;
	}

	public KqItemManager getKqItemManager() {
		return kqItemManager;
	}

	public void setKqItemManager(KqItemManager kqItemManager) {
		this.kqItemManager = kqItemManager;
	}

	public List<KqItem> getkqItems() {
		return kqItems;
	}

	public void setKqItems(List<KqItem> kqItems) {
		this.kqItems = kqItems;
	}

	public KqItem getKqItem() {
		return kqItem;
	}

	public void setKqItem(KqItem kqItem) {
		this.kqItem = kqItem;
	}

	public String getKqItemId() {
		return kqItemId;
	}

	public void setKqItemId(String kqItemId) {
		this.kqItemId = kqItemId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	/**
	 * 添加菜单
	 * @return
	 */
	public String kqItemList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("kqItemList Error", e);
		}
		return SUCCESS;
	}

	public String kqItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//列表中不显示考勤项目基础类别
			PropertyFilter filter = new PropertyFilter("NES_parentId", "-1");
			filters.add(filter);
			if (OtherUtil.measureNotNull(parentId)) {
				filters.add(new PropertyFilter("EQS_parentId", parentId));
			}
			String nodeId = getRequest().getParameter("nodeId");
			if (OtherUtil.measureNotNull(nodeId)) {
				filters.add(new PropertyFilter("EQS_kqItemId", nodeId));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqItemManager.getKqItemCriteria(pagedRequests, filters);
			this.kqItems = (List<KqItem>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			kqItemManager.save(kqItem);
			//String kqItemIcon = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/3.png";
			List<KqItem> items = kqItemManager.getByExample(kqItem);
			if (items != null && !items.isEmpty()) {
				kqItem = items.get(0);
			}
			itemNode = new KqItemTreeNode();
			itemNode.setId(kqItem.getKqItemId());
			itemNode.setpId(kqItem.getParentId());
			itemNode.setActionUrl("0");
			itemNode.setName(kqItem.getKqItemCode() + " " + kqItem.getKqItemName());
			itemNode.setIcon(this.getContextPath() + kqItem.getItemIcon());
			itemNode.setSubSysTem("ITEM");
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqItem.added" : "kqItem.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (kqItemId != null) {
			kqItem = kqItemManager.get(kqItemId);
			this.setEntityIsNew(false);
		} else {
			kqItem = new KqItem();
			kqItem.setParentId(parentId);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String kqItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//KqItem kqItem = kqItemManager.get(removeId);
					kqItemManager.remove(removeId);

				}
				gridOperationMessage = this.getText("kqItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqItemGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	/**获得所有的图标*/
	public String kqItemIconList() {
		String pathname = "/styles/themes/rzlt_theme/ihos_images/kq_item_icon";
		ServletContext context = ServletActionContext.getServletContext();
		String realPath = context.getRealPath(pathname);
		File file = new File(realPath);
		nodes = new ArrayList<ZTreeSimpleNode>();
		try {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File icon = files[i];
				String iconPath = icon.getAbsolutePath();
				String iconName = iconPath.substring(iconPath.indexOf("\\styles"));
				iconName = iconName.replaceAll("\\\\", "/");
				ZTreeSimpleNode node = new ZTreeSimpleNode();
				node.setId(iconName);
				node.setName(icon.getName());
				node.setpId("-1");
				node.setActionUrl("0");
				node.setDisCheckAble(true);
				node.setIcon(this.getContextPath() + iconName);
				nodes.add(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//图标格式：styles/themes/rzlt_theme/ihos_images/kq_item_icon/1.png
	public String makeKqItemTree() {
		String rootIcon = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/category.png";
		String kqItemIcon = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/3.png";
		kqItemTreeNodes = new ArrayList<KqItemTreeNode>();
		KqItemTreeNode rootNode = new KqItemTreeNode(), itemNode = null;
		rootNode.setId("-1");
		rootNode.setOpen(true);
		rootNode.setName("考勤项目类别");
		rootNode.setIcon(rootIcon);
		rootNode.setActionUrl("0");
		rootNode.setIsParent(true);
		rootNode.setSubSysTem("ALL");
		kqItemTreeNodes.add(rootNode);
		try {
			this.kqItems = this.kqItemManager.getAll();
			if (kqItems != null && !kqItems.isEmpty()) {
				for (KqItem kqItem : kqItems) {
					itemNode = new KqItemTreeNode();
					itemNode.setId(kqItem.getKqItemId());
					itemNode.setName(kqItem.getKqItemCode() + " " + kqItem.getKqItemName());
					itemNode.setpId(kqItem.getParentId());
					if (kqItem.getParentId().equals("-1")) {
						itemNode.setIcon(rootIcon);
						itemNode.setSubSysTem("PITEM");
					} else {
						if (kqItem.getItemIcon() == null) {
							itemNode.setIcon(kqItemIcon);
						} else {
							itemNode.setIcon(this.getContextPath() + kqItem.getItemIcon());
						}
						itemNode.setSubSysTem("ITEM");
					}
					itemNode.setActionUrl("0");
					kqItemTreeNodes.add(itemNode);
				}
			}
			Collections.sort(kqItemTreeNodes, new Comparator<KqItemTreeNode>() {
				@Override
				public int compare(KqItemTreeNode o1, KqItemTreeNode o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("makeKqItemTree Error!", e);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (kqItem == null) {
			return "Invalid kqItem Data";
		}

		return SUCCESS;

	}
}
