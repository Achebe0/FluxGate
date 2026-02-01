import React from 'react';

interface StatCardProps {
  title: string;
  value: number | string;
}

export const StatCard: React.FC<StatCardProps> = ({ title, value }) => {
  return (
    <div className="bg-gray-800/50 rounded-2xl p-6 ring-1 ring-gray-700/50">
      <h3 className="text-sm font-medium text-gray-400 uppercase tracking-wider">{title}</h3>
      <p className="mt-2 text-4xl font-bold text-white">{value}</p>
    </div>
  );
};