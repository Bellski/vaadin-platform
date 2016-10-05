package ru.vaadinp;

/**
 * @author bellski
 *
 * Интерфейс наделяет реализацию возможностью отчистить свое состояние
 */
public interface Cleanable {

	/**
	 * Вызывается при завершении приложения.
	 * Помогаем сборщику мусора.
	 */
	void clean();
}
