package ssa.cloudplatform.Binary;

import java.util.ArrayList;
import java.util.List;

import ssa.cloudplatform.Serialization.*;

public class Sandbox {

	class Level {
		private String name;
		private String path;
		private int width, height;
		private List<Entity> entities = new ArrayList<Entity>();

		public Level(String path) {
			this.name = "Level One";
			this.path = path;
			width = 64;
			height = 128;
		}

		public void add(Entity e) {
			e.init(this);
			entities.add(e);
		}

		public void update() {

		}

		public void render() {

		}

		public void save(String path) {

			CDatabase database = new CDatabase(name);

			CObject object = new CObject("LevelData");

			object.addString(CString.Create("name", name));
			object.addString(CString.Create("path", path));
			object.addField(CField.Integer("width", width));
			object.addField(CField.Integer("heigth", height));
			database.addObject(object);
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);

				CObject entity = new CObject("E" + i);
				entity.addField(CField.Integer("arrayIndex", i));
				e.serialize(entity);
				database.addObject(entity);

			}

			database.serializeToFiel(path);

		}
	}

	class Entity {

		protected int x, y;
		protected boolean removed = false;
		protected Level level;

		public Entity() {
			x = 0;
			y = 0;
		}

		public void init(Level level) {
			this.level = level;
		}

		public void serialize(CObject object) {
			object.addField(CField.Integer("x", x));
			object.addField(CField.Integer("y", y));
			object.addField(CField.Boolean("removed", removed));
		}

	}

	class Player extends Entity {
		private String name;
		private String avatarPath;

		public Player(String name, int x, int y) {
			this.x = x;
			this.y = y;

			this.name = name;
			avatarPath = "res/avatar.png";
		}

		public void serialize(CObject object) {
			super.serialize(object);
			object.addString(CString.Create("name", name));
			object.addString(CString.Create("avatarPath", avatarPath));
		}
	}

	public void play() {

		Entity mob = new Entity();
		Player player = new Player("Wais", 40, 28);
		Player p2 = new Player("Somar", 15, 0);

		Level level = new Level("res/level.lvl");
		level.add(mob);
		level.add(player);
		level.add(p2);

		level.save("levelWais.cdb");

	}

}
