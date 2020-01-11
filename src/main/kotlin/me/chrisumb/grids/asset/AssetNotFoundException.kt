package me.chrisumb.grids.asset

class AssetNotFoundException(path: String) : Exception("Asset at path \"$path\" was not found.")