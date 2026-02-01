import React from 'react';

interface ModelBadgeProps {
  modelName: string;
}

export const ModelBadge: React.FC<ModelBadgeProps> = ({ modelName }) => {
  return (
    <div className="flex justify-center mb-8">
      <span className="bg-gray-700/50 text-purple-300 text-xs font-medium me-2 px-3 py-1.5 rounded-full ring-1 ring-purple-500/50">
        {modelName}
      </span>
    </div>
  );
};