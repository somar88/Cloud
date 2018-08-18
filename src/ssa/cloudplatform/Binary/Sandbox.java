package ssa.cloudplatform.Binary;

import java.util.ArrayList;
import java.util.List;

import ssa.cloudplatform.Serialization.*;

public class Sandbox {

	static class Level {
		private String name;
		private String path;
		private int width, height;
		private List<Entity> entities = new ArrayList<Entity>();

		public Level(String path) {
			this.name = "Level One";
			this.path = path;
			width = 350;
			height = 127;
		}

		private Level() {

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
			object.addField(CField.Integer("height", height));
			object.addField(CField.Integer("entityCount", entities.size()));
			database.addObject(object);
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);

				CObject entity = new CObject("E" + i);
				byte type = 0;
				if (e instanceof Player)
					type = 1;
				entity.addField(CField.Byte("type", type));
				entity.addField(CField.Integer("arrayIndex", i));
				e.serialize(entity);
				database.addObject(entity);
			}

			database.serializeToFiel(path);

		}

		public static Level load(String path) {
			CDatabase database = CDatabase.DeseializeFromFile(path);
			CObject levelData = database.findObject("LevelData");

			Level result = new Level();
			result.name = levelData.findString("name").getString();
			result.path = levelData.findString("path").getString();
			result.width = levelData.findField("width").getInt();
			result.height = levelData.findField("height").getInt();
			int entityCount = levelData.findField("entityCount").getInt();

			for (int i = 0; i < entityCount; i++) {
				CObject entity = database.findObject("E" + i);
				Entity e;
				if (entity.findField("type").getByte() == 1)
					e = Player.deserialize(entity);
				else
					e = Entity.deserialize(entity);
				result.entities.add(e);
			}
			return result;
		}

	}

	static class Entity {

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

		public static Entity deserialize(CObject object) {
			Entity result = new Entity();
			result.x = object.findField("x").getInt();
			result.y = object.findField("y").getInt();
			result.removed = object.findField("removed").getBoolean();
			return result;
		}

	}

	static class Player extends Entity {
		private String name;
		private String avatarPath;

		public Player(String name, int x, int y) {
			this.x = x;
			this.y = y;

			this.name = name;
			avatarPath = "res/avatar.png";
		}

		private Player() {
		}

		public void serialize(CObject object) {
			super.serialize(object);
			object.addString(CString.Create("name", name));
			object.addString(CString.Create("avatarPath", avatarPath));
		}

		public static Player deserialize(CObject object) {
			Entity e = Entity.deserialize(object);
			Player result = new Player();
			result.x = e.x;
			result.y = e.y;
			result.removed = e.removed;

			result.name = object.findString("name").getString();
			result.avatarPath = object.findString("avatarPath").getString();

			return result;
		}
	}

	public void play() {
		{
			Entity mob = new Entity();
			Player player = new Player("Cherno", 40, 28);

			Level level = new Level("res/level.cdb");
			level.add(mob);
			level.add(player);

			level.save("level.cdb");
		}
		{
			Level level = Level.load("level.cdb");
			System.out.println();
		}
	}

}
